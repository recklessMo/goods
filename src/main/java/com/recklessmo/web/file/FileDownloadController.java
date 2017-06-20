package com.recklessmo.web.file;

import com.google.common.collect.Maps;
import com.recklessmo.model.dynamicTable.DynamicTable;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.excel.StudentExcelModel;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.self.ScoreSelf;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.model.system.Org;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.score.ScoreAnalyseService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.util.excel.ExcelUtils;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import com.recklessmo.web.webmodel.page.StudentPage;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 *
 * 用于文件上传
 * Created by hpf on 9/9/16.
 */
@Controller
@RequestMapping("common/file")
public class FileDownloadController {

    @Resource
    private ExamService examService;

    @Resource
    private CourseSettingService courseSettingService;

    @Resource
    private StudentService studentService;

    @Resource
    private GradeSettingService gradeSettingService;

    @Resource
    private ScoreService scoreService;

    @Resource
    private ScoreAnalyseService scoreAnalyseService;


    /**
     *
     * 下载成绩录入模板
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/score/downloadExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public void scoreFileDownload(@RequestParam("examId")long examId,  HttpServletResponse response) throws Exception{
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        //根据考试所选定的科目来进行模板表格
        Exam exam = examService.getExamById(userDetails.getOrgId(), examId);
        Map<String, Object> beans = new HashMap<>();
        beans.put("columns", exam.getCourseNameList());
        //根据考试选定的年级范围, 来将学生的学号姓名等自动导出
        List<StudentInfo> studentInfoList = studentService.getStudentListByGradeIdAndClassId(userDetails.getOrgId(), exam.getGradeId(), exam.getClassId());
        studentInfoList.sort((a, b)->{
            int gradeResult = a.getGradeName().compareTo(b.getGradeName());
            if(gradeResult == 0){
                int classResult = a.getClassName().compareTo(b.getClassName());
                if(classResult == 0){
                    return a.getSid().compareTo(b.getSid());
                }
                return classResult;
            }
            return gradeResult;
        });
        beans.put("dataList", studentInfoList);
        returnFile(beans, response, "成绩导入", "score_import",  ".xlsx");
    }

    /**
     *
     * 导出成绩单
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/score/export", method = {RequestMethod.POST, RequestMethod.GET})
    public void scoreExport(@RequestParam("examId")long examId, @RequestParam("classId")long classId, HttpServletResponse response) throws Exception {
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        //根据考试所选定的科目来进行模板表格
        Exam exam = examService.getExamById(userDetails.getOrgId(), examId);
        //表头
        List<String> labelList = new LinkedList<>();
        labelList.add("班级类型");
        labelList.add("班级类别");
        labelList.add("年级");
        labelList.add("班级");
        labelList.add("学号");
        labelList.add("姓名");
        exam.getCourseNameList().stream().forEach(courseName -> {
            labelList.add(courseName);
            labelList.add("年级排名");
            labelList.add("班级排名");
        });
        labelList.add("总分");
        labelList.add("年级排名");
        labelList.add("班级排名");
        //数据
        List<List<String>> dataList = new LinkedList<>();
        ScoreListPage scoreListPage = new ScoreListPage();
        scoreListPage.setOrgId(userDetails.getOrgId());
        scoreListPage.setExamId(examId);
        List<Score> tempList = scoreService.loadScoreList(scoreListPage);
        List<Score> scoreList = tempList;
        if(classId != 0){
            scoreList = tempList.stream().filter(o->o.getClassId() == classId).collect(Collectors.toList());
        }
        scoreList.stream().forEach(score -> {
            List<String> temp = new LinkedList<>();
            temp.add(score.getClassType());
            temp.add(score.getClassLevel());
            temp.add(score.getGradeName());
            temp.add(score.getClassName());
            temp.add(score.getSid());
            temp.add(score.getName());
            CourseScore totalCourseScore = score.getCourseScoreList().get(score.getCourseScoreList().size() - 1);
            score.getCourseScoreList().stream().forEach(courseScore -> {
                temp.add(String.valueOf(courseScore.getScore()));
                temp.add(String.valueOf(courseScore.getRank()) + (courseScore.getRank() > totalCourseScore.getRank() ? "*" : ""));
                temp.add(String.valueOf(courseScore.getClassRank()));
            });
            dataList.add(temp);
        });
        Map<String, Object> beans = new HashMap<>();
        beans.put("labelList", labelList);
        beans.put("dataList", dataList);
        beans.put("examName", exam.getExamName());
        beans.put("examTime", exam.getExamTime());
        returnFile(beans, response, "成绩单导出", "score_export", ".xlsx");
    }

    /**
     *
     * 导出进退步分析
     *
     * @param firstExamId
     * @param secondExamId
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/rankchange/export", method = {RequestMethod.POST, RequestMethod.GET})
    public void rankchangeExport(@RequestParam("first")long firstExamId, @RequestParam("second")long secondExamId, HttpServletResponse response) throws Exception {
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        Exam firstExam = examService.getExamById(userDetails.getOrgId(), firstExamId);
        Exam secondExam = examService.getExamById(userDetails.getOrgId(), secondExamId);

        //根据考试所选定的科目来进行模板表格
        List<Score> firstList = scoreService.loadScoreByExamId(userDetails.getOrgId(), firstExamId);
        List<Score> secondList = scoreService.loadScoreByExamId(userDetails.getOrgId(), secondExamId);
        DynamicTable dynamicTable = (DynamicTable)scoreAnalyseService.analyseRankChange(firstList, secondList);
        //表头
        List<String> labelList = dynamicTable.getLabelList().stream().map(o->o.getTitle()).collect(Collectors.toList());
        //数据
        List<List<String>> dataList = new LinkedList<>();
        dynamicTable.getDataList().stream().forEach(single -> {
            List<String> temp = new LinkedList<String>();
            dynamicTable.getLabelList().stream().forEach(label -> {
                temp.add(String.valueOf(single.get(label.getField())));
            });
            dataList.add(temp);
        });
        Map<String, Object> beans = new HashMap<>();
        beans.put("labelList", labelList);
        beans.put("dataList", dataList);
        beans.put("examName1", firstExam.getExamName());
        beans.put("examName2", secondExam.getExamName());
        returnFile(beans, response, "进退步分析", "rank_change_export", ".xlsx");
    }

    /**
     *
     * 下载学生信息导入文件
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/student/downloadExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public void studentFileDownload(HttpServletResponse response) throws Exception {
        returnFile(null, response, "学生导入模板", "student_import", ".xlsx");
    }


    /**
     *
     * 导出学生信息文件
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/student/export", method = {RequestMethod.POST, RequestMethod.GET})
    public void studentExport(@RequestParam("gradeId")long gradeId, @RequestParam("classId")long classId, HttpServletResponse response) throws Exception {
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        StudentPage studentPage = new StudentPage();
        studentPage.setOrgId(userDetails.getOrgId());
        studentPage.setGradeId(gradeId);
        studentPage.setClassId(classId);
        studentPage.setPage(1);
        studentPage.setCount(10000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<StudentInfo> studentAllInfoList = studentService.getStudentInfo(studentPage);
        studentAllInfoList.stream().forEach(studentInfo -> {
            studentInfo.setBirthStr(sdf.format(studentInfo.getBirth()));
        });
        Map<String, Object> beans = Maps.newHashMap();
        beans.put("dataList", studentAllInfoList);
        returnFile(beans, response, "学生信息导出", "student_export", ".xlsx");
    }


    /**
     *
     * 下载账号信息导入文件
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/account/downloadExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public void accountFileDownload(HttpServletResponse response) throws Exception {
        returnFile(null, response, "账号导入模板", "account_import", ".xlsx");
    }

    /**
     *
     * 测试
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    public void getFileContent(HttpServletResponse response) throws Exception{
        List<Org> orgList = new LinkedList<>();
        Org org1 = new Org();
        org1.setOrgName("xxxx");
        Org org2 = new Org();
        org2.setOrgName("yyyy");
        orgList.add(org1);
        orgList.add(org2);
        Map<String, Object> beans = Maps.newHashMap();
        beans.put("dataList", orgList);
        returnFile(beans, response, "test", "test", ".xlsx");
    }


    private void returnFile(Map<String, Object> beans, HttpServletResponse response, String fileName, String templateName, String fileExt) throws Exception{
        if(beans == null){
            beans = new HashMap<>();
        }
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName + fileExt, "UTF-8"));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("excel/" + templateName + fileExt);
        ExcelTransformer transformer = new ExcelTransformer();
        transformer.registerFuncs("dateFormat", new SimpleDateFormat("yyyy-MM-dd"));
        Workbook workbook = transformer.transform(inputStream, beans);
        Sheet sheet = workbook.getSheetAt(0);
        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }


    public static void main(String[] args){

    }
}
