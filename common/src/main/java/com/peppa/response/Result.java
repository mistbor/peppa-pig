package com.peppa.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    private Integer code;
    private Object data;
    private Object msg;

    public Result(Integer code, Object msg) {
        this.code = code;
        this.msg = msg;
    }
}
