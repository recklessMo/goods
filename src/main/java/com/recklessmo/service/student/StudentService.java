package com.recklessmo.service.student;

import com.recklessmo.dao.student.StudentDAO;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentAllInfo;
import com.recklessmo.model.student.StudentBaseInfo;
import com.recklessmo.model.student.StudentGradeInfo;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.web.webmodel.page.StudentPage;
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 7/23/16.
 */
@Service
public class StudentService {

    @Resource
    private StudentDAO studentDAO;

    @Resource
    private GradeSettingService gradeSettingService;

    /**
     * 用于搜索显示
     * @param page
     * @return
     */
    public List<StudentBaseInfo> getStudentBaseInfo(StudentPage page){
        return studentDAO.getStudentBaseInfoList(page);
    }

    /**
     * 用于搜索指定条件下的搜索总数,用于分页
     * @param page
     * @return
     */
    public int getStudentBaseInfoTotalCount(StudentPage page){
        return studentDAO.getStudentBaseInfoListTotalCount(page);
    }

    /**
     * 用于插入学生信息
     * @param studentAddInfo
     */
    public void insertStudentAddInfo(StudentAddInfo studentAddInfo){
        studentDAO.insertStudentAddInfo(studentAddInfo);
    }


    /**
     * 用于批量插入学生信息
     * 每次插入500个
     */
    public void insertStudentList(List<StudentAddInfo> studentList){
        int gap = 500;
        int start = 0;
        while(start < studentList.size()) {
            int end = start + gap;
            if(end > studentList.size()){
                end = studentList.size();
            }
            List<StudentAddInfo> sub = studentList.subList(start, end);
            studentDAO.insertStudentList(sub);
            start = end;
        }
    }

    /**
     * 用于导出显示
     * @param page
     * @return
     */
    public List<StudentAllInfo> getStudentAllInfo(StudentPage page){
        return studentDAO.getStudentAllInfo(page);
    }

    /**
     *
     * 用于导出
     *
     * @param page
     * @return
     */
    public int getStudentAllInfoTotalCount(StudentPage page){
        return studentDAO.getStudentAllInfoTotalCount(page);
    }


    /**
     *
     *  根据微信id获取学生信息
     *
     * @param wechatId
     * @return
     */
    public StudentAllInfo getStudentInfoByWechatId(String wechatId){
        return studentDAO.getStudentAllInfoByWechatId(wechatId);
    }

    /**
     *
     * 根据sid获取学生信息
     *
     * @param sid
     * @return
     */
    public StudentAllInfo getStudentInfoBySid(String sid){
        return studentDAO.getStudentInfoBySid(sid);
    }


    /**
     *
     * 根据gradeid和classid来获取对应的学生信息
     *
     * @param gradeId
     * @param classId
     * @return
     */
    public List<StudentAllInfo> getStudentListByGradeIdAndClassId(long gradeId, long classId){
        return studentDAO.getStudentListByGradeIdAndClassId(gradeId, classId);
    }


    public List<StudentGradeInfo> getStudentGradeInfoBySidList(List<String> sidList){
        return studentDAO.getStudentGradeInfoBySidList(sidList);
    }


    /**
     *
     * 根据examid和searchstr查询学生信息
     *
     * 用于进行个人综合分析页面的实现
     *
     * @param page
     * @return
     */
    public List<StudentGradeInfo> searchStudentByExam(StudentPage page){
        List<StudentGradeInfo> studentGradeInfoList =  studentDAO.searchStudentByExam(page);
        compose(studentGradeInfoList);
        return studentGradeInfoList;
    }


    /**
     * 根据学号idList获取学生的信息
     * @param sidList
     * @return
     */
    public List<StudentBaseInfo> getStudentBaseInfoByIdList(List<String> sidList){
        return studentDAO.getStudentBaseInfoByIdList(sidList);
    }




    private void compose(List<StudentGradeInfo> studentGradeInfoList){
        List<Grade> gradeList = gradeSettingService.listAllGrade();
        Map<Long, String> gradeMap = new HashMap<>();
        Map<Long, String> classMap = new HashMap<>();
        gradeList.stream().forEach(grade-> {
            gradeMap.put(grade.getGradeId(), grade.getGradeName());
            grade.getClassList().stream().forEach(group -> {
                classMap.put(group.getClassId(), group.getClassName());
            });
        });

        studentGradeInfoList.stream().forEach(studentGradeInfo -> {
            studentGradeInfo.setGradeName(gradeMap.get(studentGradeInfo.getGradeId()));
            studentGradeInfo.setClassName(classMap.get(studentGradeInfo.getClassId()));
        });
    }


}
