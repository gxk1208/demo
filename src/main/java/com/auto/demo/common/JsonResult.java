package com.auto.demo.common;

import java.io.Serializable;

/**
 *
 *
 * @since 2019/7/1
 * @param <T>
 */
public class JsonResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public JsonResult() {

    }

    public static <T> JsonResult<T> success(){
        return new JsonResult(200);
    }

    public static <T> JsonResult<T> success(T data){
        return new JsonResult<T>(200,data);
    }

    public static <T> JsonResult<T> failed(String msg){
        return new JsonResult(500,msg);
    }

    public static <T> JsonResult<T> msg(String msg){
        return new JsonResult(10000,msg);
    }

    public static <T> JsonResult<T> loginFail(String msg){
        return new JsonResult(9999, msg);
    }


    public static <T> JsonResult<T> failed(Integer code, String msg){
        return new JsonResult(code,msg);
    }

    public JsonResult(Integer code) {
        this.code = code;
    }

    public JsonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
