package com.linchtech.fileservice.controller;

import lombok.Getter;

/**
 * @author 107
 * @create 2018-07-23 10:24
 * @desc
 **/
@Getter
public enum HttpResult {

    /**
     * 请求成功返回码.
     */
    SUCCESS("请求成功", 0),

    /**
     * 请求失败返回描述及返回码.
     */
    FAIL("请求失败", -1),



    /**
     * 不合法的参数返回描述及返回码.
     */
    PARAMETER_ERROR("不合法的参数", 1),
    METHOD_ERROR("不支持的请求方式", 2),

    /**
     * 系统异常返回描述及返回码.
     */
    SYSTEM_ERROR("系统错误", 3);

    private int code;

    private String message;

    HttpResult(String message, int code) {
        this.code = code;
        this.message = message;
    }
}
