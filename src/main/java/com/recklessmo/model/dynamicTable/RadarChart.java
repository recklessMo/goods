package com.recklessmo.model.dynamicTable;

import java.util.List;

/**
 * Created by hpf on 4/8/17.
 */
public class RadarChart {

    private List<String> nameList;
    private List<RadarChartDimen> typeList;
    private List<RadarChartInner> dataList;


    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<RadarChartDimen> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<RadarChartDimen> typeList) {
        this.typeList = typeList;
    }

    public List<RadarChartInner> getDataList() {
        return dataList;
    }

    public void setDataList(List<RadarChartInner> dataList) {
        this.dataList = dataList;
    }
}
