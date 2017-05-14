package com.recklessmo.model.common;

/**
 * Created by hpf on 14/05/2017.
 */
public class Pair {
    private long id;
    private String value;

    public Pair(){

    }

    public Pair(long id, String value){
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
