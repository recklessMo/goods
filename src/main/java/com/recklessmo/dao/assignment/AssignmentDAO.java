package com.recklessmo.dao.assignment;

import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public interface AssignmentDAO {

    List<Assignment> listAssignment(Page page);
    int listAssingmentCount(Page page);
    void addAssignment(Assignment assignment);

}
