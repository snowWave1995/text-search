package com.snowwave.textsearch.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by zhangfuqiang on 2018/1/26.
 */
public class TextUtil {

    public static String TEXT_DOMAIN = "http://127.0.0.1:8080/";
    public static String FILE_DIR = "D:/upload/";
    public static String[] TEXT_FILE_EXTD = new String[] {"txt", "doc", "pdf", "xml"};


    /**
     * 判断是否合法
     * @param ext
     * @return
     */
    public static boolean isFileAllowed(String ext) {
        for (String format:TEXT_FILE_EXTD) {
            if (ext.toLowerCase().equals(format)) {
                return true;
            }
        }
        return false;
    }
    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

}
