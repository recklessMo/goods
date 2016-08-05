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


}
