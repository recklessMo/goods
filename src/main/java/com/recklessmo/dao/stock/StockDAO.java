package com.recklessmo.dao.stock;

import com.recklessmo.model.stock.Goods;
import com.recklessmo.model.stock.Stock;
import com.recklessmo.model.stock.StockItem;
import com.recklessmo.web.webmodel.page.GoodsPage;
import com.recklessmo.web.webmodel.page.StockPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/5/16.
 */
public interface StockDAO {

    List<Goods> listGoods(GoodsPage page);
    int listGoodsCount(GoodsPage page);
    void addGoods(Goods goods);
    void updateGoods(Goods goods);
    void deleteGoods(@Param("id") long id, @Param("orgId")long orgId, @Param("status")int status);


    List<Stock> listStock(StockPage page);
    int listStockCount(StockPage page);


    void addStock(Stock stock);

    void addStockItem(StockItem stockItem);

    Stock getStock(@Param("id")long id, @Param("orgId")long orgId);

    List<StockItem> getStockItemsByGoodsId(@Param("goodsId")long goodsId);

    void updateGoodsCount(@Param("goodsId")long goodsId, @Param("count")int count);
}
