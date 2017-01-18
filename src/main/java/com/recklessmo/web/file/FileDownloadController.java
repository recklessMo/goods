package com.recklessmo.web.file;

import com.recklessmo.util.excel.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * 用于文件上传
 * Created by hpf on 9/9/16.
 */
@Controller
@RequestMapping("common/file")
public class FileDownloadController {

    public static String SCORE_FILE = "成绩录入";
    public static String SCORE_FILE_EXT = "xlsx";

    public static String STUDENT_FILE = "学生录入";
    public static String STUDENT_FILE_EXT = "xlsx";


    private void returnFile(Workbook workbook, HttpServletResponse response, String fileName, String fileExt) throws Exception{
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName + "." + fileExt, "UTF-8"));
        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }

    /**
     *
     * 下载成绩录入模板
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/score/downloadExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public void scoreFileDownload(HttpServletResponse response) throws Exception{
        List<String> rowNames = new LinkedList<>();
        setScoreColumns(rowNames);
        Workbook workbook = ExcelUtils.createWorkbook(SCORE_FILE_EXT);
        Sheet sheet = workbook.createSheet(SCORE_FILE);
        Row row = sheet.createRow(0);
        for (int i = 0; i < rowNames.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rowNames.get(i));
        }
        returnFile(workbook, response, SCORE_FILE, SCORE_FILE_EXT);
    }


    private void setScoreColumns(List<String> colNames){
        colNames.add("学号");
        colNames.add("姓名");
        colNames.add("语文");
        colNames.add("数学");
        colNames.add("英语");
        colNames.add("政治");
        colNames.add("历史");
        colNames.add("地理");
        colNames.add("物理");
        colNames.add("化学");
        colNames.add("生物");
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
        List<String> rowNames = getStudentColumns();
        Workbook workbook = ExcelUtils.createWorkbook(STUDENT_FILE_EXT);
        Sheet sheet = workbook.createSheet(STUDENT_FILE);
        Row row = sheet.createRow(0);
        for (int i = 0; i < rowNames.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rowNames.get(i));
        }
        returnFile(workbook, response, STUDENT_FILE, STUDENT_FILE_EXT);
    }

    private List<String> getStudentColumns(){
        LinkedList<String> studentColumns = new LinkedList<>();
        studentColumns.add("姓名");
        studentColumns.add("曾用名");
        studentColumns.add("监护人手机");
        studentColumns.add("身份证号");
        studentColumns.add("性别");
        studentColumns.add("出生年月");
        studentColumns.add("户口所在地");
        studentColumns.add("民族");
        studentColumns.add("籍贯");
        studentColumns.add("家庭住址");
        studentColumns.add("qq号码");
        studentColumns.add("微信号码");
        studentColumns.add("学号");
        studentColumns.add("年级");
        studentColumns.add("班级");
        studentColumns.add("班内职务");
        return studentColumns;
    }


    public static void main(String[] args){

    }
}
