package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 10/28/16.
 */
public class ScoreListPage extends Page{
    private long examId;
    //排序类型, 取值为1,2,3,4,5,6,7, 分别对英语总分和单科
    private int sort;
    //查询的是哪个班级的数据
    private long cid;

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
