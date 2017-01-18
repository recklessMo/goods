package com.recklessmo.web.file;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.ScorePage;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
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

    /**
     *
     * 上传成绩
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/score/uploadExcel", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse scoreFileUpload(@RequestParam("file")MultipartFile multipartFile) throws Exception{
        //处理excel文件
        InputStream inputStream = multipartFile.getInputStream();
        DataFormatter dataFormatter = new DataFormatter();
        Workbook workbook = WorkbookFactory.create(inputStream);
        List<Score> data = new LinkedList<>();
        int totalSheets = workbook.getNumberOfSheets();
        if(totalSheets != 0 ){
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet) {
                if(row.getRowNum() == 0){
                    continue;
                }
                Score score = new Score();
                int colNums = row.getLastCellNum();
                for(int j = row.getFirstCellNum(); j < colNums; j++){
                    Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
                    if(cell != null){
                        //处理值;都转换成string之后进行具体解析
                        String value = dataFormatter.formatCellValue(cell);
                        switch (j){
                            case 0:score.setSid(Long.parseLong(value));
                                break;
                            case 1:score.setName(value);
                                break;
                            case 2:score.setChinese(Long.parseLong(value));
                                break;
                            case 3:score.setMath(Long.parseLong(value));
                                break;
                            case 4:score.setEnglish(Long.parseLong(value));
                                break;
                            case 5:score.setPolotics(Long.parseLong(value));
                                break;
                            case 6:score.setHistory(Long.parseLong(value));
                                break;
                            case 7:score.setGeo(Long.parseLong(value));
                                break;
                            case 8:score.setPhysics(Long.parseLong(value));
                                break;
                            case 9:score.setChemistry(Long.parseLong(value));
                                break;
                            case 10:score.setBiology(Long.parseLong(value));
                                break;
                        }
                    }else{
                        //抛出异常,不允许有为空的情况,缺失的都必须用0来代替
                        //全部为0代表缺考
                    }
                }
                data.add(score);
            }
        }else{
            //抛出异常,无数据
        }
        //处理score list
        //采用replace方法存储成绩
        //只进行存储
        scoreService.insertScoreList(data);
        return new JsonResponse(200, null, null);
    }

    /**
     *
     * 上传学生信息
     *
     * 学号信息暂时做成非自动生成, 初高中学生班级变化多.
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/student/uploadExcel", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse studentFileUpload(@RequestParam("file")MultipartFile multipartFile) throws Exception{
        //处理excel文件
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        InputStream inputStream = multipartFile.getInputStream();
        DataFormatter dataFormatter = new DataFormatter();
        Workbook workbook = WorkbookFactory.create(inputStream);
        List<StudentAddInfo> data = new LinkedList<>();
        Map<String, Long> gradeMap = getGradeMap(0L);
        Map<String, Long> classMap = getClassMap(0L);
        int totalSheets = workbook.getNumberOfSheets();
        if(totalSheets != 0 ){
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet) {
                if(row.getRowNum() == 0){
                    continue;
                }
                StudentAddInfo studentAddInfo = new StudentAddInfo();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                int colNums = row.getLastCellNum();
                for(int j = row.getFirstCellNum(); j < colNums; j++){
                    Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
                    if(cell != null){
                        //处理值;都转换成string之后进行具体解析
                        String value = dataFormatter.formatCellValue(cell);
                        value = value.trim();
                        switch (j){
                            case 0: studentAddInfo.setName(value);
                                break;
                            case 1: studentAddInfo.setOtherName(value);
                                break;
                            case 2: studentAddInfo.setPhone(value);
                                break;
                            case 3: studentAddInfo.setScn(value);
                                break;
                            case 4: studentAddInfo.setGender("男".equals(value) ? 0 : 1);
                                break;
                            case 5: studentAddInfo.setBirth(sdf.parse(value));
                                break;
                            case 6: studentAddInfo.setBirthTown(value);
                                break;
                            case 7: studentAddInfo.setPeople(value);
                                break;
                            case 8: studentAddInfo.setHomeTown(value);
                                break;
                            case 9: studentAddInfo.setAddress(value);
                                break;
                            case 10: studentAddInfo.setQq(value);
                                break;
                            case 11: studentAddInfo.setWechat(value);
                                break;
                            case 12: studentAddInfo.setSid(0);
                                break;
                            case 13:
                                studentAddInfo.setGradeName(value);
                                studentAddInfo.setGradeId(gradeMap.get(value));
                                break;
                            case 14:
                                studentAddInfo.setClassName(value);
                                studentAddInfo.setClassId(classMap.get(studentAddInfo.getGradeName() + "_" + value));
                                break;
                            case 15: studentAddInfo.setJob(value);
                                break;
                            default:
                                break;
                        }
                    }else{
                        //抛出异常,不允许有为空的情况,缺失的都必须用0来代替
                        //全部为0代表缺考
                    }
                }
                data.add(studentAddInfo);
            }
        }else{
            //抛出异常,无数据
        }
        studentService.insertStudentList(data);
        return new JsonResponse(200, null, null);
    }

    private Map<String, Long> getGradeMap(long orgId){
        List<Grade> gradeList = gradeSettingService.listAllGrade();
        Map<String, Long> res = new HashMap<>();
        for(Grade grade : gradeList){
            res.put(grade.getGradeName(), grade.getGradeId());
        }
        return res;
    }

    private Map<String, Long> getClassMap(long orgId){
        List<Grade> gradeList = gradeSettingService.listAllGrade();
        Map<String, Long> res = new HashMap<>();
        for(Grade grade : gradeList){
            for(Group group : grade.getClassList()){
                res.put(grade.getGradeName()+ "_" + group.getClassName(), group.getClassId());
            }
        }
        return res;
    }

}
