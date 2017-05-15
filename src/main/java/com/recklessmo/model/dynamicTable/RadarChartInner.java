package com.recklessmo.model.dynamicTable;

import java.util.List;

/**
 * Created by hpf on 4/8/17.
 */
public class RadarChartInner {

    private String name;
    private List<Double> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }
}
