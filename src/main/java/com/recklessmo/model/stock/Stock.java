package com.recklessmo.model.stock;

import java.util.List;

/**
 * Created by hpf on 8/5/16.
 */
public class Stock {

    private long id;
    private String stockType;
    private long userId;
    private String userName;
    private String ghdw;
    private long cost;
    private String category;
    private List<StockItem> items;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGhdw() {
        return ghdw;
    }

    public void setGhdw(String ghdw) {
        this.ghdw = ghdw;
    }

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

    public List<StockItem> getItems() {
        return items;
    }

    public void setItems(List<StockItem> items) {
        this.items = items;
    }
}
