package com.example.rainbowend.Entity;

import lombok.Data;

/**
 * Rainbow
 *
 * @DATE:2023/7/31 0031
 */
@Data
public class ResponseResult<T> {
    private String status;
    private String message;
    private T data;

    public ResponseResult() {
    }

    public static ResponseResult ok() {
        return create("0", null, null);
    }

    public static ResponseResult ok(String message) {
        return create("0", message, null);
    }

    public static <T> ResponseResult ok(String message, T data) {
        return create("0", message, data);
    }

    public static <T> ResponseResult ok(T data) {
        return create("0", null, data);
    }

    public static ResponseResult error() {
        return create("1", null, null);
    }

    public static ResponseResult error(String message) {
        return create("1", message, null);
    }

    public static <T> ResponseResult error(String message, T data) {
        return create("1", message, data);
    }

    private static <T> ResponseResult create(String status, String message, T data) {
        ResponseResult t = new ResponseResult();
        t.setStatus(status);
        t.setMessage(message);
        t.setData(data);
        return t;
    }

}