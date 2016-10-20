package com.recklessmo.web.stock;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.stock.Goods;
import com.recklessmo.model.stock.Stock;
import com.recklessmo.model.stock.StockItem;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.stock.StockService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.GoodsPage;
import com.recklessmo.web.webmodel.page.StockPage;
import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
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
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        goods.setInUserName(defaultUserDetails.getUsername());
        stockService.addGoods(goods);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/goods/update", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse updateGoods(@RequestBody Goods goods){
        //TODO 为了避免脏数据,可以采用annotation的方式来进行非空校验
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        goods.setUpdated(new Date());
        stockService.updateGoods(goods);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/goods/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse deleteGoods(@RequestBody long id){
        //TODO 为了避免脏数据,可以采用annotation的方式来进行非空校验
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        stockService.deleteGoods(id);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/goods/history/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse listGoodsHistory(@RequestBody long goodsId){
        List<StockItem> data = stockService.getStockItemsByGoodsId(goodsId);
        return new JsonResponse(200, data, null);
    }

    /**
     *
     * 入库
     *
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/in/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse listInStock(@RequestBody StockPage page){
        int count = stockService.listStockCount(page);
        List<Stock> data = stockService.listStock(page);
        return new JsonResponse(200, data, count);
    }

    @ResponseBody
    @RequestMapping(value = "/in/add", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse addInStock(@RequestBody Stock stock){
        //TODO do some checking here to keep integrity
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        stock.setUserId(defaultUserDetails.getId());
        stock.setUserName(defaultUserDetails.getUsername());
        stockService.addStock(stock);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/in/detail", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse listInStockDetail(@RequestBody long id){
        Stock stock = stockService.getStockById(id);
        return new JsonResponse(200, stock, null);
    }

    /**
     * 用于出库
     * @param page
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/out/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse listOutStock(@RequestBody StockPage page){
        int count = stockService.listStockCount(page);
        List<Stock> data = stockService.listStock(page);
        return new JsonResponse(200, data, count);
    }

    @ResponseBody
    @RequestMapping(value = "/out/add", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse addOutStock(@RequestBody Stock stock){
        //TODO do some checking here to keep integrity
        DefaultUserDetails userDetail = ContextUtils.getLoginUserDetail();
        stock.setUserName(userDetail.getUsername());
        stock.setUserId(userDetail.getId());
        stockService.addStock(stock);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/out/detail", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse listOutStockDetail(@RequestBody long id){
        Stock stock = stockService.getStockById(id);
        return new JsonResponse(200, stock, null);
    }


}
