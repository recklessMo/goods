package com.recklessmo.dao.oplog;

import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.model.oplog.OpLog;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public interface OpLogDAO {

    List<OpLog> listOpLog(Page page);
    int listOpLogCount(Page page);
    void addOpLog(OpLog opLog);

}
