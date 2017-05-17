package com.recklessmo.service.assignment;

import com.recklessmo.dao.assignment.AssignmentDAO;
import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class AssignmentService {

    @Resource
    private AssignmentDAO assignmentDAO;


    public List<Assignment> listAssignment(Page page){
        return assignmentDAO.listAssignment(page);
    }

    public int listAssignmentCount(Page page){
        return assignmentDAO.listAssingmentCount(page);
    }

    public void addAssignment(Assignment assignment){
        assignmentDAO.addAssignment(assignment);
    }


}
