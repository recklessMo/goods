package com.recklessmo.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by hpf on 7/13/16.
 */
public class EncryptUtils {

    public static String getSha1String(String data){
        return DigestUtils.sha1Hex(data);
    }

    public static String getMd5Hex(String data){
        return DigestUtils.md5Hex(data);
    }

}
