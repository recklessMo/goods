package com.recklessmo.dao.assignment;

import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.assignment.AssignmentStatus;
import com.recklessmo.web.webmodel.page.AssignmentListPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public interface AssignmentStatusDAO {

    void addAssignmentStatus(AssignmentStatus assignmentStatus);
    AssignmentStatus getAssignmentStatus(@Param("orgId") long orgId, @Param("sid")String sid, @Param("id") long id);
    List<AssignmentStatus> getAssignmentStatusList(@Param("id")long id);

}
