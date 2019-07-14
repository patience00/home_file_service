package com.linchtech.fileservice.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 107
 * @create 2018-07-23 10:17
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {

    private Integer code;

    private String message;

    private T data;

    /**
     * 请求成功
     *
     * @return
     */
    public static ResultVO ok() {
        return ResultVO.builder()
                .code(HttpResult.SUCCESS.getCode())
                .message(HttpResult.SUCCESS.getMessage())
                .build();
    }

    public static <T> ResultVO<T> ok(T data) {
        return ok(HttpResult.SUCCESS, data);
    }

    public static <T> ResultVO<T> ok(HttpResult httpResult, T data) {
        return ResultVO.<T>builder()
                .code(httpResult.getCode())
                .message(httpResult.getMessage())
                .data(data)
                .build();
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static ResultVO fail() {
        return ResultVO.builder()
                .code(HttpResult.FAIL.getCode())
                .message(HttpResult.FAIL.getMessage())
                .build();
    }

    public static <T> ResultVO<T> fail(HttpResult httpResult) {
        return fail(httpResult, null);
    }

    public static <T> ResultVO<T> fail(T data) {
        return fail(HttpResult.FAIL, data);
    }

    public static <T> ResultVO<T> fail(HttpResult httpResult, T data) {
        return ResultVO.<T>builder()
                .code(httpResult.getCode())
                .message(httpResult.getMessage())
                .data(data)
                .build();
    }

    public static ResultVO fail(String message) {
        return ResultVO.builder()
                .code(HttpResult.FAIL.getCode())
                .message(message)
                .data(null)
                .build();
    }

}
