package com.recklessmo.model.score.result;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class GapInner {

    private String cname;
    private List<Integer> countList=new LinkedList<>();

    public GapInner(){

    }

    public GapInner(int count){
        countList.clear();
        for(int i = 0 ; i < count; i++){
            countList.add(0);
        }
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
