package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 8/5/16.
 */
public class StockPage extends Page{

    private String stockType;
    private String category;



    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
