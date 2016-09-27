package com.recklessmo.util.excel;

import com.recklessmo.model.ExcelObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * 用于操作excel, 适度封装一下
 *
 * Created by hpf on 8/31/16.
 */
public class ExcelUtils {

    /**
     *
     * 从文件中读取数据
     *
     * 通过该方法来对excel文档进行解析,然后通过反射填充字段,返回一个list
     *
     * @param object
     * @param <T>
     * @return
     */
     public static <T> List<T> parseExcelFile(T object, String fileName){

         return null;
     }


    /**
     *
     * 从流获取对应的数据
     *
     * @param object
     * @param inputStream
     * @param <T>
     * @return
     */
     public static <T> List<T> parseExcelInputStream(T object, InputStream inputStream){

         return null;
     }

    /**
     *
     * 将学生信息导出供录入成绩使用
     *
     */
    public static void createExcelFileWithStudentInfo(String fileName, String fileType, ExcelObject data){


    }

    public static void createExcelFileWithStudentScore(String fileName, String fileType, ExcelObject data){

    }


    public static Workbook createWorkbook(String fileType){
        if(fileType.equals("xls")){
            return new HSSFWorkbook();
        }else{
            return new XSSFWorkbook();
        }
    }

}
