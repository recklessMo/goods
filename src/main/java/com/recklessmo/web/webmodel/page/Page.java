package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 6/21/16.
 */
public class Page {

    private int page = 1;
    //限定一个默认值, 即使没有传,也要限制,不能一次性拉太多.
    private int count = 5000;
    private long orgId;
    private String searchStr;
    private int type = 1;
    private String sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public int getStartIndex(){
        return (page - 1) * count;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
