package com.recklessmo.service.assignment;

import com.recklessmo.dao.assignment.AssignmentDAO;
import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.web.webmodel.page.AssignmentListPage;
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
    private GradeSettingService gradeSettingService;

    @Resource
    private AssignmentDAO assignmentDAO;


    public List<Assignment> listAssignment(AssignmentListPage page) {
        List<Assignment> assginmentList = assignmentDAO.listAssignment(page);
        composeAssignment(assginmentList, page.getOrgId());
        return assginmentList;
    }

    public int listAssignmentCount(AssignmentListPage page){
        return assignmentDAO.listAssignmentCount(page);
    }

    public void addAssignment(Assignment assignment){

        assignmentDAO.addAssignment(assignment);
    }

    public void deleteAssignment(long orgId, long id){
        assignmentDAO.deleteAssignment(orgId, id);
    }

    public Assignment getAssignment(long orgId, long id){
        return assignmentDAO.getAssignment(orgId, id);
    }

    private void composeAssignment(List<Assignment> assignments, long orgId){
        List<Grade> gradeList = gradeSettingService.listAllGrade(orgId);
        Map<Long, Grade> gradeMap = new HashMap<>();
        Map<Long, Group> classMap = new HashMap<>();
        gradeList.stream().forEach(grade-> {
            gradeMap.put(grade.getGradeId(), grade);
            grade.getClassList().stream().forEach(group -> {
                classMap.put(group.getClassId(), group);
            });
        });

        assignments.stream().forEach(assignment -> {
            Grade grade = gradeMap.get(assignment.getGradeId());
            if(grade != null){
                assignment.setGradeName(grade.getGradeName());
                assignment.setGradeOtherName(grade.getOtherName());
            }
            Group group = classMap.get(assignment.getClassId());
            if(group != null){
                assignment.setClassName(group.getClassName());
            }
        });
    }

}
