package com.recklessmo.dao.stock;

import com.recklessmo.model.stock.Goods;
import com.recklessmo.model.stock.Stock;
import com.recklessmo.web.webmodel.page.GoodsPage;
import com.recklessmo.web.webmodel.page.StockPage;

import java.util.List;

/**
 * Created by hpf on 8/5/16.
 */
public interface StockDAO {

    List<Goods> listGoods(GoodsPage page);
    int listGoodsCount(GoodsPage page);
    void addGoods(Goods goods);


    List<Stock> listInStock(StockPage page);
    int listInStockCount(StockPage page);


}
