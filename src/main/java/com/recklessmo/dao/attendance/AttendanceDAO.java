package com.recklessmo.dao.attendance;

import com.recklessmo.model.attendance.Attendance;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public interface AttendanceDAO {

    List<Attendance> listAttendance(Page page);

    int listAttendanceCount(Page page);

    void addAttendance(Attendance attendance);

    void deleteAttendance(@Param("orgId")long orgId, @Param("id")long id);

}
