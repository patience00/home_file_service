package com.linchtech.fileservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: 107
 * @date: 2019/1/14 15:05
 * @description:
 * @Review:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParameterException extends RuntimeException {

    private Integer code;
    private String message;

    public ParameterException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ParameterException build(String message) {
        return new ParameterException(-1, message);
    }

}
