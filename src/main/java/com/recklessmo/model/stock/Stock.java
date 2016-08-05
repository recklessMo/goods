package com.recklessmo.model.stock;

/**
 * Created by hpf on 8/5/16.
 */
public class Stock {

    private long id;
    private String stockType;
    private String ghdw;
    private long userId;
    private String userName;
    private long cost;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getGhdw() {
        return ghdw;
    }

    public void setGhdw(String ghdw) {
        this.ghdw = ghdw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

}
