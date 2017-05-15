package com.recklessmo.model.dynamicTable;

import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 4/8/17.
 */
public class DynamicChart {

    private List<String> xList;
    private List<String> typeList;
    private List<List<Double>> dataList;

    public List<String> getxList() {
        return xList;
    }

    public void setxList(List<String> xList) {
        this.xList = xList;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<List<Double>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Double>> dataList) {
        this.dataList = dataList;
    }
}
