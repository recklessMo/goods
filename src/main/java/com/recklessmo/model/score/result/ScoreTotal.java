package com.recklessmo.model.score.result;

/**
 * Created by hpf on 10/12/16.
 */
public class ScoreTotal {

    //代表考试id
    private long examId;
    //代表是全部年级,还是分开的班级
    private long cid;
    //代表具体的分析结果, 内存结构的json表示
    private String detail;

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
