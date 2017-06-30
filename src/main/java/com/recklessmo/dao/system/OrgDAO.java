package com.recklessmo.dao.system;

import com.recklessmo.model.system.Org;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 2/20/17.
 */
public interface OrgDAO {


    List<Org> listOrg(Page page);
    int listOrgCount(Page page);

    void addOrg(Org org);
    Org getOrg(@Param("orgId") long orgId);

    long getMaxOrgId();

}
