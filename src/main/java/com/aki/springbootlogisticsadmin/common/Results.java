package com.aki.springbootlogisticsadmin.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Results implements Serializable {
    private int statusCode;
    private String message;
    private Object data;


    public static Results successRes(Object data) {
        return successRes(200, "操作成功！", data);
    }

    public static Results successRes(int statusCode, String message, Object data) {
        Results res = new Results();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setData(data);
        return res;
    }

    public static Results failRes(String message) {
        return failRes(400, message, null);
    }

    public static Results failRes(String message, Object data) {
        return failRes(400, message, data);
    }

    public static Results failRes(int statusCode, String message, Object data) {
        Results res = new Results();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setData(data);
        return res;
    }
}
