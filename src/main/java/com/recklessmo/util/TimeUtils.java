package com.recklessmo.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by hpf on 4/13/16.
 */
public class TimeUtils {

    public static String NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(NORMAL_FORMAT);
}
