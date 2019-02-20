package com.peppa.exception;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.peppa.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException be) {
        log.info("MyExceptionHandler handleBusinessException : ", be);
        return getErrorResponse(be.getMessage(), be.getCode());
    }

    @ExceptionHandler(HystrixBadRequestException.class)
    public ResponseEntity hystrixBadRequestException(HystrixBadRequestException hbre) {
        log.info("MyExceptionHandler HystrixBadRequestException : ", hbre);
        Result result = toResult(hbre);
        if (result == null || result.getMsg() == null) {
            throw hbre;
        }
        return getErrorResponse((String) result.getMsg(), result.getCode());
    }

    private ResponseEntity<Result> getErrorResponse(String message, Integer code) {
        Result result = new Result();
        result.setMsg(message);
        result.setCode(code);
        return new ResponseEntity<>(result, HttpStatus.NOT_EXTENDED);
    }

    private Result toResult(HystrixBadRequestException hbre) {
        if (hbre == null) {
            return null;
        }
        JSONObject obj = JSONObject.parseObject(hbre.getMessage());
        Integer code = obj.getInteger("code");
        if (code == null) {
            return null;
        }

        String message = obj.getString("msg");
        if (message == null) {
            return null;
        }

        return new Result(code, message);
    }
}
