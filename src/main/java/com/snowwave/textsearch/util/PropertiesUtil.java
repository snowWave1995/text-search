package com.snowwave.textsearch.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by zhangfuqiang on 2018/1/25.
 */
@Slf4j
public class PropertiesUtil {

    private static Properties props;

    //静态代码块优于普通代码块，普通代码块优于构造器
    static {
        String fileName = "mmall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常",e);
        }
    }

    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(value == null){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());
        if(value == null){
            value = defaultValue;
        }
        return value.trim();
    }
}
