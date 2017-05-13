package com.recklessmo.web.file;

import com.google.common.collect.Maps;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.excel.StudentExcelModel;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.model.system.Org;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.util.excel.ExcelUtils;
import com.recklessmo.web.webmodel.page.Page;
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
        Exam exam = examService.getExamById(examId);
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
        List<StudentInfo> studentAllInfoList = studentService.getStudentInfo(studentPage);
        Map<String, Object> beans = Maps.newHashMap();
        beans.put("dataList", studentAllInfoList);
        returnFile(beans, response, "学生信息导出", "student_export", ".xlsx");
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
