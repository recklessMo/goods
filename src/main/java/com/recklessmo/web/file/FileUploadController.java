package com.recklessmo.web.file;

import com.recklessmo.response.JsonResponse;
import com.recklessmo.web.webmodel.page.ScorePage;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 用于文件上传
 * Created by hpf on 9/9/16.
 */
@Controller
@RequestMapping("common/file")
public class FileUploadController {

    @RequestMapping(value = "/score/uploadExcel", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse scoreFileUpload(@RequestParam("file")MultipartFile multipartFile) throws Exception{
        //处理excel文件
        InputStream inputStream = multipartFile.getInputStream();
        DataFormatter dataFormatter = new DataFormatter();
        Workbook workbook = WorkbookFactory.create(inputStream);
        List<ScorePage> data = new LinkedList<>();
        int totalSheets = workbook.getNumberOfSheets();
        if(totalSheets != 0 ){
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet) {
                if(row.getRowNum() == 0){
                    continue;
                }
                ScorePage score = new ScorePage();
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
//                            case 2:score.setEid(Long.parseLong(value));
//                                break;
                            case 2:score.setChinese(Long.parseLong(value));
                                break;
                            case 3:score.setMath(Long.parseLong(value));
                                break;
                            case 4:score.setEnglish(Long.parseLong(value));
                                break;
                            case 5:score.setPolitics(Long.parseLong(value));
                                break;
                            case 6:score.setHistory(Long.parseLong(value));
                                break;
                            case 7:score.setGeography(Long.parseLong(value));
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
                    }
                }
                data.add(score);
            }
        }else{
            //抛出一场,无数据
        }

        //处理score list



        return new JsonResponse(200, null, null);
    }



}