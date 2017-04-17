package com.recklessmo.dao.student;

import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentAllInfo;
import com.recklessmo.model.student.StudentBaseInfo;
import com.recklessmo.model.student.StudentGradeInfo;
import com.recklessmo.web.webmodel.page.StudentPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 7/23/16.
 *
 */
public interface StudentDAO {

    List<StudentBaseInfo> getStudentBaseInfoList(StudentPage page);
    int getStudentBaseInfoListTotalCount(StudentPage page);
    void insertStudentAddInfo(StudentAddInfo studentAddInfo);
    void insertStudentList(@Param("list") List<StudentAddInfo> studentAddInfoList);

    List<StudentAllInfo> getStudentAllInfo(StudentPage page);
    int getStudentAllInfoTotalCount(StudentPage page);

    StudentAllInfo getStudentAllInfoByWechatId(@Param("wechatId")String wechatId);
    StudentAllInfo getStudentInfoBySid(@Param("sid")String sid);
    List<StudentAllInfo> getStudentListByGradeIdAndClassId(@Param("gradeId")long gradeId, @Param("classId")long classId);

    List<StudentGradeInfo> getStudentGradeInfoBySidList(@Param("sidList")List<String> sidList);

    List<StudentGradeInfo> searchStudentByExam(StudentPage page);

    List<StudentBaseInfo> getStudentBaseInfoByIdList(@Param("sidList")List<String> sidList);
}

