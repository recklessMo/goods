package com.recklessmo.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hpf on 4/13/16.
 */
public class TimeUtils {

    public static String NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(NORMAL_FORMAT);

    public static void main(String[] args){
//        List<Integer> data = new ArrayList<>();
//        data.add(1);
//        data.add(2);
//        data.add(3);
////        Collections.rotate(data, 1);
//
//        System.out.println(8%6);
//        System.out.println((-8)%6);
//
//        List<Integer> xyz = new LinkedList<>();
//        for(int i = 0; i < 20000000; i++){
//            for(int j = 0; j < 10000; j++) {
//                xyz.add(i);
//            }
//        }

        ConcurrentHashMap<String, String> x = new ConcurrentHashMap<>();
        x.put("x", "x");
        x.put("y", "yy");
        System.out.println(x.get("x"));

    }

}

