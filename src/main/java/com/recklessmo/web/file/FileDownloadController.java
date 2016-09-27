package com.recklessmo.web.file;

import com.recklessmo.util.excel.ExcelUtils;
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

    @RequestMapping(value = "/score/downloadExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public void scoreFileDownload(HttpServletResponse response) throws Exception{
        List<String> rowNames = new LinkedList<>();
        setColumns(rowNames);
        Workbook workbook = ExcelUtils.createWorkbook(SCORE_FILE_EXT);
        Sheet sheet = workbook.createSheet(SCORE_FILE);
        Row row = sheet.createRow(0);
        for (int i = 0; i < rowNames.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rowNames.get(i));
        }
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(SCORE_FILE + "." + SCORE_FILE_EXT, "UTF-8"));
        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }


    private void setColumns(List<String> colNames){
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


    public static void main(String[] args){

    }
}
