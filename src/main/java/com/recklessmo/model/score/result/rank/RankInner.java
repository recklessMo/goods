package com.recklessmo.model.score.result.rank;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class RankInner {

    private long cid;
    private String cname;
    private List<Integer> countList=new LinkedList<>();

    public RankInner(){

    }

    public RankInner(int count){
        countList.clear();
        for(int i = 0 ; i < count; i++){
            countList.add(0);
        }
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<Integer> getCountList() {
        return countList;
    }

    public void setCountList(List<Integer> countList) {
        this.countList = countList;
    }
}
