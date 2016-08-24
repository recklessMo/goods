package com.recklessmo.web.setting;

import com.recklessmo.model.setting.Term;
import com.recklessmo.model.setting.Year;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.YearSettingService;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.YearPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
@Controller
@RequestMapping("/v1/setting")
public class YearSettingController {

    @Resource
    private YearSettingService yearSettingService;

    @RequestMapping(value = "/year/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addYear(@RequestBody Year year){
        //TODO do some check here
        yearSettingService.addYear(year);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/year/update", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse updateYear(@RequestBody Year year){
        //TODO do some check here
        yearSettingService.updateYear(year);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/year/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listYear(@RequestBody Page page){
        //TODO do some check here
        int count = yearSettingService.listYearCount(page);
        List<Year> yearList = yearSettingService.listYear(page);
        return new JsonResponse(200, yearList, count);
    }

    @RequestMapping(value = "/term/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addTerm(@RequestBody Term term){
        //TODO do some check here
        yearSettingService.addTerm(term);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/term/update", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse updateTerm(@RequestBody Term term){
        //TODO do some check here
        yearSettingService.updateTerm(term);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/term/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listTerm(@RequestBody YearPage page){
        //TODO do some check here
        int count = yearSettingService.listTermCount(page);
        List<Term> termList = yearSettingService.listTerm(page);
        return new JsonResponse(200, termList, count);
    }



}
