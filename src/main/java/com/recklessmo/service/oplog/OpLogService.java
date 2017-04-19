package com.recklessmo.service.oplog;

import com.recklessmo.dao.graduate.GraduateDAO;
import com.recklessmo.dao.oplog.OpLogDAO;
import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.model.oplog.OpLog;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class OpLogService {

    @Resource
    private OpLogDAO opLogDAO;

    public List<OpLog> getOpLogList(Page page){
        return opLogDAO.listOpLog(page);
    }

    public int getOpLogListCount(Page page){
        return opLogDAO.listOpLogCount(page);
    }

    public void addOpLog(OpLog opLog){
        opLogDAO.addOpLog(opLog);
    }

}
