package com.recklessmo.model.dynamicTable;

import com.recklessmo.model.score.Score;

import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 4/8/17.
 */
public class DynamicTable {

    private List<TableColumn> labelList;
    private List<Map<String, Object>> dataList;

    public List<TableColumn> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<TableColumn> labelList) {
        this.labelList = labelList;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }
}
