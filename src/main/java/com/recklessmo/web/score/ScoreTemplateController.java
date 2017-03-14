package com.recklessmo.web.score;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreTemplateService;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 用于模板构建
 *
 * Created by hpf on 9/30/16.
 */
@Controller
@RequestMapping("/v1/template")
public class ScoreTemplateController {

    @Resource
    private ScoreTemplateService scoreTemplateService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse add(@RequestBody ScoreTemplate scoreTemplate){
        scoreTemplateService.save(scoreTemplate);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse list(@RequestBody Page page){
        List<ScoreTemplate> scoreTemplates = scoreTemplateService.getList(page);
        int cnt = scoreTemplateService.countList(page);
        return new JsonResponse(200, scoreTemplates, cnt);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse delete(@RequestBody long id){
        scoreTemplateService.delete(id);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse get(@RequestParam("id")long id){
        return new JsonResponse(200, null, null);
    }



    static class A{
        private Map<String, Inner> res = new HashMap<>();
        private String name;

        public Map<String, Inner> getRes() {
            return res;
        }

        public void setRes(Map<String, Inner> res) {
            this.res = res;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static  class Inner{
        private long a;
        private long b;

        public long getA() {
            return a;
        }

        public void setA(long a) {
            this.a = a;
        }

        public long getB() {
            return b;
        }

        public void setB(long b) {
            this.b = b;
        }
    }

    public static void main(String[] args){


        Map<String, Inner> res = new HashMap<>();
        Inner i1 = new Inner();
        i1.setA(1);
        i1.setB(2);
        Inner i2 = new Inner();
        i2.setA(11);
        i2.setB(22);
        res.put("语文", i1);
        res.put("数学", i2);

        A a = new A();
        a.setName("xxx");
        a.setRes(res);

        System.out.println(JSON.toJSONString(a));
        A x = JSON.parseObject("{\"name\":\"xxx\",\"res\":{\"数学\":{\"a\":11,\"b\":22},\"语文\":{\"a\":1,\"b\":2}}}", A.class);

        ScoreTemplate scoreTemplate = JSON.parseObject("{\"created\":1483200000000,\"name\":\"xyz\",\"courseTotalSettingMap\":{\"语文\":{\"qualified\":60,\"best\":90,\"good\":80,\"full\":100}},\"detail\":\"xx\",\"id\":1,\"type\":0,\"updated\":1489336232000}\n", ScoreTemplate.class);

        System.out.println("123");
        System.out.println("3456");
        return;

    }

}
