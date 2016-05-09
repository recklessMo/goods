package com.recklessmo.web.passport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hpf on 4/26/16.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/test")
    public String indexTest(){
        return "index-test";
    }
}
