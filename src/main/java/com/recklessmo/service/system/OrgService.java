package com.recklessmo.service.system;

import com.recklessmo.dao.system.OrgDAO;
import com.recklessmo.model.system.Org;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 2/20/17.
 */
@Service
public class OrgService {

    @Resource
    private OrgDAO orgDAO;

    public List<Org> listOrg(Page page){
        return orgDAO.listOrg(page);
    }

    public int listOrgCount(Page page){
        return orgDAO.listOrgCount(page);
    }

    public Org getOrg(long orgId){
        return orgDAO.getOrg(orgId);
    }

    public void addOrg(Org org){
        orgDAO.addOrg(org);
    }

    public long getMaxOrgId(){
        return orgDAO.getMaxOrgId();
    }

}
