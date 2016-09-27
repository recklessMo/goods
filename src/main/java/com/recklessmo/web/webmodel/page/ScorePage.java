package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 8/5/16.
 */
public class ScorePage{

    private long sid;
    private String name;
    private long eid;

    private long chinese;
    private long math;
    private long english;
    private long politics;
    private long history;
    private long geography;
    private long physics;
    private long chemistry;
    private long biology;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getEid() {
        return eid;
    }

    public void setEid(long eid) {
        this.eid = eid;
    }

    public long getChinese() {
        return chinese;
    }

    public void setChinese(long chinese) {
        this.chinese = chinese;
    }

    public long getMath() {
        return math;
    }

    public void setMath(long math) {
        this.math = math;
    }

    public long getEnglish() {
        return english;
    }

    public void setEnglish(long english) {
        this.english = english;
    }

    public long getPolitics() {
        return politics;
    }

    public void setPolitics(long politics) {
        this.politics = politics;
    }

    public long getHistory() {
        return history;
    }

    public void setHistory(long history) {
        this.history = history;
    }

    public long getGeography() {
        return geography;
    }

    public void setGeography(long geography) {
        this.geography = geography;
    }

    public long getPhysics() {
        return physics;
    }

    public void setPhysics(long physics) {
        this.physics = physics;
    }

    public long getChemistry() {
        return chemistry;
    }

    public void setChemistry(long chemistry) {
        this.chemistry = chemistry;
    }

    public long getBiology() {
        return biology;
    }

    public void setBiology(long biology) {
        this.biology = biology;
    }
}
