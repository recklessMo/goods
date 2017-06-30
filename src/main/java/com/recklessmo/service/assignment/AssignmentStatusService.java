package com.recklessmo.service.assignment;

import com.recklessmo.dao.assignment.AssignmentDAO;
import com.recklessmo.dao.assignment.AssignmentStatusDAO;
import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.assignment.AssignmentStatus;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.web.webmodel.page.AssignmentListPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class AssignmentStatusService {

    @Resource
    private AssignmentStatusDAO assignmentStatusDAO;

    public void addAssignmentStatus(AssignmentStatus assignmentStatus){
        assignmentStatusDAO.addAssignmentStatus(assignmentStatus);
    }


    public AssignmentStatus getAssignmentStatus(long orgId, String sid, long id){
        return assignmentStatusDAO.getAssignmentStatus(orgId, sid, id);
    }

    public List<AssignmentStatus> getAssignmentStatusList(long id){
        return assignmentStatusDAO.getAssignmentStatusList(id);
    }

}
