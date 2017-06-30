package com.recklessmo.model.score.result.self;

import java.util.List;

/**
 * Created by hpf on 4/10/17.
 */
public class ScoreSelfInner {

    public String name;
    public List<Double> value;

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
