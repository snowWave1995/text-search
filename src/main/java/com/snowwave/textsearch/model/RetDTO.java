package com.snowwave.textsearch.model;

import org.springframework.http.HttpStatus;

/**
 * Created by zhangfuqiang on 2018/5/25.
 */
public class RetDTO<T> extends BaseModel{
    private int code;
    private String msg;
    private T data;

    public RetDTO(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public RetDTO(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <E> RetDTO<E> getReturnJson(E data){
        return new RetDTO<E>(HttpStatus.OK.value(), HttpStatus.OK.name(), data);
    }


    public static <E> RetDTO<E> getReturnJson(String msg, E data){
        return new RetDTO<E>(HttpStatus.OK.value(), msg, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
