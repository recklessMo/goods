package com.recklessmo.annotation;

import java.lang.annotation.*;

/**
 *
 * 这个annotation用于进行excel导入的时候进行自动化的set
 *
 * Created by hpf on 8/31/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface col {
    String value();
}
