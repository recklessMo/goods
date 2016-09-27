package com.recklessmo.model;

import java.util.List;

/**
 * Created by hpf on 9/12/16.
 */
public class ExcelObject {

    private List<String> rowNames;

    private List<Object> rowData;

    public List<String> getRowNames() {
        return rowNames;
    }

    public void setRowNames(List<String> rowNames) {
        this.rowNames = rowNames;
    }

    public List<Object> getRowData() {
        return rowData;
    }

    public void setRowData(List<Object> rowData) {
        this.rowData = rowData;
    }
}
