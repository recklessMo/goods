package com.recklessmo.service.stock;

import com.recklessmo.dao.stock.StockDAO;
import com.recklessmo.model.stock.Goods;
import com.recklessmo.model.stock.Stock;
import com.recklessmo.model.stock.StockItem;
import com.recklessmo.web.webmodel.page.GoodsPage;
import com.recklessmo.web.webmodel.page.StockPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hpf on 8/5/16.
 */
@Service
public class StockService {

    @Resource
    private StockDAO stockDAO;

    /**
     *
     * 物资
     * @param page
     * @return
     */
    public List<Goods> listGoods(GoodsPage page){
        page.setStatus(Goods.GOODS_NORMAL);
        return stockDAO.listGoods(page);
    }

    public int listGoodsCount(GoodsPage page){
        page.setStatus(Goods.GOODS_NORMAL);
        return stockDAO.listGoodsCount(page);
    }

    public void addGoods(Goods goods){
        stockDAO.addGoods(goods);
    }

    public void updateGoods(Goods goods){
        stockDAO.updateGoods(goods);
    }

    public void deleteGoods(long id){
        stockDAO.deleteGoods(id, Goods.GOODS_DELETE);
    }

    /**
     *
     * 入库
     * @param page
     * @return
     */
    public List<Stock> listStock(StockPage page){
        return stockDAO.listStock(page);
    }

    public int listStockCount(StockPage page){
        return stockDAO.listStockCount(page);
    }

    /**
     *
     * 入库和出库
     *
     * @param stock
     */
    public void addStock(Stock stock){
        stock.setUserId(1L);
        stock.setUserName("习大大");
        stock.setCost(100);
        stock.setGhdw("海马科技");
        //首先添加stock
        stockDAO.addStock(stock);
        //TODO 为了简单先写个单个添加的dao方法,以后可以换成批量添加的,这样提高效率
        for(StockItem stockItem : stock.getItems()){
            stockItem.setStockId(stock.getId());
            stockItem.setStockType(stock.getStockType());
            stockItem.setCategory(stock.getCategory());
            stockDAO.addStockItem(stockItem);
            stockDAO.updateGoodsCount(stockItem.getGoodsId(),
                    stockItem.getStockType().equals("入库")? stockItem.getCount() : -stockItem.getCount());
        }
    }

    /**
     *
     * 获取出入库明细
     *
     * @param id
     * @return
     */
    public Stock getStockById(long id){
        return stockDAO.getStock(id);
    }

    /**
     *
     * 获取出入库历史
     *
     * @param goodsId
     * @return
     */
    public List<StockItem> getStockItemsByGoodsId(long goodsId){
        return stockDAO.getStockItemsByGoodsId(goodsId);
    }

}
