package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 6/21/16.
 */
public class Page {

    private int page;
    private int count;
    private long orgId;
    private String searchStr;

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
