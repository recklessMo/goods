package com.recklessmo.service.stock;

import com.recklessmo.dao.stock.StockDAO;
import com.recklessmo.model.stock.Goods;
import com.recklessmo.model.stock.Stock;
import com.recklessmo.web.webmodel.page.GoodsPage;
import com.recklessmo.web.webmodel.page.StockPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/5/16.
 */
@Service
public class StockService {

    @Resource
    private StockDAO stockDAO;

    public List<Goods> listGoods(GoodsPage page){
        return stockDAO.listGoods(page);
    }

    public int listGoodsCount(GoodsPage page){
        return stockDAO.listGoodsCount(page);
    }

    public void addGoods(Goods goods){
        stockDAO.addGoods(goods);
    }

    public List<Stock> listInStock(StockPage page){
        return stockDAO.listInStock(page);
    }

    public int listInStockCount(StockPage page){
        return stockDAO.listInStockCount(page);
    }
}
