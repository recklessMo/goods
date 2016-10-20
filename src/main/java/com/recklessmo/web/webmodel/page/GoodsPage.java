package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 8/5/16.
 */
public class GoodsPage extends Page{
    private long status;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
