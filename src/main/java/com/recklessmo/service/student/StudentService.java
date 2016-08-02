package com.recklessmo.service.student;

import com.recklessmo.dao.student.StudentDAO;
import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentBaseInfo;
import com.recklessmo.web.webmodel.page.StudentPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 7/23/16.
 */
@Service
public class StudentService {

    @Resource
    private StudentDAO studentDAO;

    public List<StudentBaseInfo> getStudentBaseInfo(StudentPage page){
        return studentDAO.getStudentBaseInfoList(page);
    }

    public void insertStudentAddInfo(StudentAddInfo studentAddInfo){
        studentDAO.insertStudentAddInfo(studentAddInfo);
    }

}
