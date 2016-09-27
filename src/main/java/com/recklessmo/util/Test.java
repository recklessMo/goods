package com.recklessmo.util;

import org.eclipse.jetty.util.Fields;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by hpf on 8/31/16.
 */
public class Test {


    public void testMethod(){

        Class testSample = Test.class;
        Method[] methods = testSample.getMethods();
        for(Method method : methods){
            //method.invoke();
            method.getName().equals("set");
        }
        Field[] fields = testSample.getFields();
        for(Field field : fields){
            field.getAnnotations();
//            field.
        }

    }

    public static void main(String[] args){

    }

}
