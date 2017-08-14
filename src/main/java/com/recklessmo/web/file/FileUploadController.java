package com.recklessmo.web.file;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.model.user.User;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.service.user.UserService;
import com.recklessmo.util.ContextUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用于文件上传
 * Created by hpf on 9/9/16.
 */
@Controller
@RequestMapping("common/file")
public class FileUploadController {

    @Resource
    private ScoreService scoreService;

    @Resource
    private GradeSettingService gradeSettingService;

    @Resource
    private StudentService studentService;

    @Resource
    private CourseSettingService courseSettingService;

    @Resource
    private ExamService examService;

    @Resource
    private UserService userService;

    /**
     * 上传成绩
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/score/uploadExcel", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse scoreFileUpload(@RequestParam("examId") long examId, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        //处理excel文件
        InputStream inputStream = multipartFile.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);
        List<Score> data = new LinkedList<>();
        List<String> labelList = null;
        StringBuilder errMsg = new StringBuilder();
        //获取courseMap
        Map<String, Long> courseNameToIdMap = getCourseNameToIdMap();
        int totalSheets = workbook.getNumberOfSheets();
        if (totalSheets != 0) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    labelList = processHeadLabel(row);
                    //校验表头是否符合要求
                    if (checkHead(labelList, examId)) {
                        continue;
                    } else {
                        throw new Exception("表头不正确");
                    }
                }
                Score score = processRow(courseNameToIdMap, examId, labelList, row, errMsg);
                data.add(score);
            }
        } else {
            //抛出异常,无数据
            throw new Exception("表格无内容");
        }
        //处理score list
        if (errMsg.length() > 0) {
            //返回错误信息
            return new JsonResponse(300, "数据错误!", null);
        } else {
            preProcessData(examId, userDetails.getOrgId(), data);
            scoreService.removeScoreList(userDetails.getOrgId(), examId);
            scoreService.insertScoreList(data);
            examService.updateExamStatus(examId, Exam.EXAM_UPLOADED, new Date());
            return new JsonResponse(200, null, null);
        }
    }

    private void preProcessData(long examId, long orgId, List<Score> scoreList) {
        List<String> sidList = scoreList.stream().map(o -> o.getSid()).collect(Collectors.toList());
        List<StudentInfo> studentInfoList = studentService.getStudentInfoBySidList(orgId, sidList);
        Map<String, StudentInfo> studentInfoMap = studentInfoList.stream().collect(Collectors.toMap(StudentInfo::getSid, Function.identity()));
        scoreList.stream().forEach(item -> {
            item.setOrgId(orgId);
            item.setExamId(examId);
            StudentInfo studentInfo = studentInfoMap.get(item.getSid());
            item.setGradeId(studentInfo.getGradeId());
            item.setClassId(studentInfo.getClassId());
        });
    }

    private List<String> processHeadLabel(Row row) throws Exception {
        List<String> labelList = new LinkedList<>();
        int colNums = row.getLastCellNum();
        //处理label
        for (int t = row.getFirstCellNum(); t < colNums; t++) {
            Cell cell = row.getCell(t, Row.RETURN_BLANK_AS_NULL);
            if (cell == null) {
                throw new Exception("表头不能为空");
            } else {
                labelList.add(cell.getStringCellValue().trim());
            }
        }
        return labelList;
    }

    private boolean checkHead(List<String> labelList, long examId) throws Exception {
        //check 是否表头有修改
        if (!labelList.contains("学号")) {
            return false;
        }
        return true;
    }

    private Score processRow(Map<String, Long> courseMap, long examId, List<String> labelList, Row row, StringBuilder sb) throws Exception {
        Score score = new Score();
        score.setExamId(examId);
        DataFormatter dataFormatter = new DataFormatter();
        for (int j = 0; j < labelList.size(); j++) {
            String label = labelList.get(j);
            Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
            if ("姓名".equals(label) || "年级".equals(label) || "班级".equals(label)) {
            } else if ("学号".equals(label)) {
                if (cell != null) {
                    String value = dataFormatter.formatCellValue(cell);
                    score.setSid(value);
                } else {
                    int rowNumber = row.getRowNum();
                    sb.append("第");
                    sb.append(rowNumber);
                    sb.append("行, 第");
                    sb.append(j);
                    sb.append("列, 为空!");
                }
            } else {
                CourseScore courseScore = new CourseScore();
                courseScore.setCourseName(label);
                courseScore.setCourseId(courseMap.get(label));
                if (cell != null) {
                    String value = dataFormatter.formatCellValue(cell);
                    //处理值;都转换成string之后进行具体解析
                    courseScore.setScore(Double.parseDouble(value));
                } else {
                    //增加缺考标记
                    courseScore.setFlag(1);
                    courseScore.setScore(0.0);
                }
                score.getCourseScoreList().add(courseScore);
            }
        }
        return score;
    }

    /**
     * 上传学生信息
     * <p>
     * 学号信息暂时做成非自动生成, 初高中学生班级变化多.
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/student/uploadExcel", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse studentFileUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        //处理excel文件
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        InputStream inputStream = multipartFile.getInputStream();
        DataFormatter dataFormatter = new DataFormatter();
        Workbook workbook = WorkbookFactory.create(inputStream);
        List<StudentInfo> data = new LinkedList<>();
        Map<String, Long> gradeMap = getGradeMap(defaultUserDetails.getOrgId());
        Map<String, Long> classMap = getClassMap(defaultUserDetails.getOrgId());
        int totalSheets = workbook.getNumberOfSheets();
        if (totalSheets != 0) {
            Sheet sheet = workbook.getSheetAt(0);
            int cnt = 10001;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                if (row.getRowNum() >= 10000){
                    break;
                }
                System.out.println("process row num ： " + row.getRowNum());
                StudentInfo studentInfo = new StudentInfo();
                int colNums = row.getLastCellNum();
                for (int j = row.getFirstCellNum(); j < colNums; j++) {
                    Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
                    String value = null;
                    if (cell != null) {
                        //处理值;都转换成string之后进行具体解析
                        value = dataFormatter.formatCellValue(cell).trim();
                    }
                    switch (j) {
//                        case 0:
//                            checkCell(value, row.getRowNum(), j+1);
//                            studentInfo.setSid(value);
//                            break;
//                        case 1:
//                            checkCell(value, row.getRowNum(), j+1);
//                            studentInfo.setGradeName(value);
//                            Long gradeId = gradeMap.get(value);
//                            if(gradeId == null) {
//                                throw new Exception("年级名称:" + value + " 不存在!");
//                            }
//                            studentInfo.setGradeId(gradeId);
//                            break;
//                        case 2:
//                            checkCell(value, row.getRowNum(), j+1);
//                            studentInfo.setClassName(value);
//                            Long classId = classMap.get(studentInfo.getGradeName()+"_" + value);
//                            if(classId == null) {
//                                throw new Exception("班级名称:" + value + " 不存在!");
//                            }
//                            studentInfo.setClassId(classId);
//                            break;
//                        case 3:
//                            checkCell(value, row.getRowNum(), j+1);
//                            studentInfo.setName(value);
//                            break;
//                        case 4:
//                            studentInfo.setOtherName(value);
//                            break;
//                        case 5:
//                            studentInfo.setJob(value);
//                            break;
//                        case 6:
//                            studentInfo.setPhone(value);
//                            break;
//                        case 7:
//                            studentInfo.setScn(value);
//                            break;
//                        case 8:
//                            checkCell(value, row.getRowNum(), j+1);
//                            studentInfo.setGender("男".equals(value) ? 0 : 1);
//                            break;
//                        case 9:
//                            studentInfo.setBirth(sdf.parse(value));
//                            break;
//                        case 10:
//                            studentInfo.setBirthTown(value);
//                            break;
//                        case 11:
//                            studentInfo.setPeople(value);
//                            break;
//                        case 12:
//                            studentInfo.setHomeTown(value);
//                            break;
//                        case 13:
//                            studentInfo.setAddress(value);
//                            break;
//                        case 14:
//                            studentInfo.setQq(value);
//                            break;
//                        case 15:
//                            studentInfo.setWechat(value);
//                            break;
                        case 0:
                            break;
                        case 1:
                            checkCell(value, row.getRowNum(), j+1);
                            studentInfo.setGradeName(value);
                            Long gradeId = gradeMap.get(value);
                            if(gradeId == null) {
                                throw new Exception("年级名称:" + value + " 不存在!");
                            }
                            studentInfo.setGradeId(gradeId);
                            break;
                        case 2:
                            checkCell(value, row.getRowNum(), j+1);
                            studentInfo.setClassName(value);
                            Long classId = classMap.get(studentInfo.getGradeName()+"_" + value);
                            if(classId == null) {
                                throw new Exception("班级名称:" + value + " 不存在!");
                            }
                            studentInfo.setClassId(classId);
                            break;
                        case 3:
                            checkCell(value, row.getRowNum(), j+1);
                            studentInfo.setName(value);
                            break;
                        case 4:
                            checkCell(value, row.getRowNum(), j+1);
                            studentInfo.setGender("男".equals(value) ? 0 : 1);
                            break;
//                        case 5:
//                            studentInfo.setBirth(sdf.parse(value));
//                            break;
                        case 6:
                            studentInfo.setParentName(value);
                            break;
                        case 7:
                            studentInfo.setPhone(value);
                            break;
                        default:
                            break;
                    }
                }
                studentInfo.setOrgId(defaultUserDetails.getOrgId());
                String temp = "" + cnt;
                cnt++;
                studentInfo.setSid(studentInfo.getGradeName().substring(0, studentInfo.getGradeName().length() - 1) + temp.substring(1));
                System.out.println("sid: " + studentInfo.getSid());
                data.add(studentInfo);
            }
        }
        if(data.size() > 0) {
            studentService.insertStudentList(data);
        }
        return new JsonResponse(200, null, null);
    }

    private void checkCell(String value, int row, int col) throws Exception{
        if(value == null) {
            throw new Exception("(第" + row + "行, 第" + col + "列)不能为空！");
        }
    }

    private Map<String, Long> getGradeMap(long orgId) {
        List<Grade> gradeList = gradeSettingService.listAllGrade(orgId);
        Map<String, Long> res = new HashMap<>();
        for (Grade grade : gradeList) {
            res.put(grade.getGradeName(), grade.getGradeId());
        }
        return res;
    }

    private Map<String, Long> getClassMap(long orgId) {
        List<Grade> gradeList = gradeSettingService.listAllGrade(orgId);
        Map<String, Long> res = new HashMap<>();
        for (Grade grade : gradeList) {
            for (Group group : grade.getClassList()) {
                res.put(grade.getGradeName() + "_" + group.getClassName(), group.getClassId());
            }
        }
        return res;
    }

    private Map<String, Long> getCourseNameToIdMap(){
        List<Course> courseList = courseSettingService.getStandardCourseList();
        Map<String, Long> result = courseList.stream().collect(Collectors.toMap(Course::getCourseName, course -> course.getCourseId()));
        return result;
    }


    /**
     * 上传账号信息， 进行批量导入
     *
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/account/uploadExcel", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse accountFileUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        //处理excel文件
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        InputStream inputStream = multipartFile.getInputStream();
        DataFormatter dataFormatter = new DataFormatter();
        Workbook workbook = WorkbookFactory.create(inputStream);
        List<User> userList = new LinkedList<>();
        Date now = new Date();
        int totalSheets = workbook.getNumberOfSheets();
        if (totalSheets != 0) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0 || row.getRowNum() == 1) {
                    continue;
                }
                User user = new User();
                int colNums = row.getLastCellNum();
                for (int j = row.getFirstCellNum(); j < colNums; j++) {
                    Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
                    String value = null;
                    if (cell != null) {
                        //处理值;都转换成string之后进行具体解析
                        value = dataFormatter.formatCellValue(cell).trim();
                    }
                    switch (j) {
                        case 0:
                            break;
                        case 1:
                            //账号名称
                            checkCell(value, row.getRowNum(), j+1);
                            user.setUserName(value);
                            break;
                        case 2:
                            //姓名
                            checkCell(value, row.getRowNum(), j+1);
                            user.setName(value);
                            break;
                        case 3:
                            //性别
                            checkCell(value, row.getRowNum(), j+1);
                            user.setGender(value.trim().equals("男") ? 0 : 1);
                            break;
                        case 4:
                            //手机
                            user.setPhone(value);
                            break;
                        case 5:
                            //职务
                            user.setJob(value);
                            break;
                        default:
                            break;
                    }
                }
                user.setOrgId(defaultUserDetails.getOrgId());
                //默认权限
                user.setAuthorities("110,210,220,230,240,250,260,270,280,310,320,330,410,420,430,510,520,530,540,810,820,830");
                //密码默认为
                user.setPwd("yunxiaoyuan");
                user.setCreated(now);
                userList.add(user);
            }
        }
        if(userList.size() > 0) {
            userService.insertUserList(userList);
        }
        return new JsonResponse(200, null, null);
    }
}
