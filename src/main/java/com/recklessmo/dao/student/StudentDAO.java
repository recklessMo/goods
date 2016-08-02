package com.recklessmo.dao.student;

import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentBaseInfo;
import com.recklessmo.web.webmodel.page.StudentPage;

import java.util.List;

/**
 * Created by hpf on 7/23/16.
 *
 */
public interface StudentDAO {

    List<StudentBaseInfo> getStudentBaseInfoList(StudentPage page);
    void insertStudentAddInfo(StudentAddInfo studentAddInfo);

}

