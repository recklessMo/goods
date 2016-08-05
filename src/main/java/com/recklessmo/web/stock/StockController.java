package com.recklessmo.web.stock;

import com.recklessmo.model.stock.Goods;
import com.recklessmo.model.stock.Stock;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.stock.StockService;
import com.recklessmo.web.webmodel.page.GoodsPage;
import com.recklessmo.web.webmodel.page.StockPage;
import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 7/7/16.
 */
@Controller
@RequestMapping("/v1/stock")
public class StockController {

    @Resource
    private StockService stockService;


    /**
     * 获取goods列表,物资列表
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/goods/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse listGoods(@RequestBody GoodsPage page){
        int totalCount = stockService.listGoodsCount(page);
        List<Goods> data = stockService.listGoods(page);
        return new JsonResponse(200, data, totalCount);
    }

    @ResponseBody
    @RequestMapping(value = "/goods/add", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse addGoods(@RequestBody Goods goods){
        //TODO 为了避免脏数据,可以采用annotation的方式来进行非空校验
        stockService.addGoods(goods);
        return new JsonResponse(200, null, null);
    }


    @ResponseBody
    @RequestMapping(value = "/in/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse listInStock(@RequestBody StockPage page){
        int count = stockService.listInStockCount(page);
        List<Stock> data = stockService.listInStock(page);
        return new JsonResponse(200, data, count);
    }
}
