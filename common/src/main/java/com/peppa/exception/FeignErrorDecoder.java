package com.peppa.exception;


import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class FeignErrorDecoder extends ErrorDecoder.Default {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("FeignErrorDecoder.decode(): method key: " + methodKey);
        HystrixBadRequestException hbre = decodeBusinessException(response);
        if (hbre != null) {
            log.info("FeignErrorDecoder.decode(): receive business exception! return HystrixBadRequestException");
            return hbre;
        }
        log.info("FeignErrorDecoder.decode(): return super.decode()");
        return super.decode(methodKey, response);
    }

    private HystrixBadRequestException decodeBusinessException(Response response) {
        String body = getBody(response);
        if (body == null) {
            return null;
        }

        JSONObject obj = JSONObject.parseObject(body);
        Integer code = obj.getInteger("code");
        if (code == null) {
            return null;
        }

        String message = obj.getString("msg");
        if (message == null) {
            return null;
        }

        return new HystrixBadRequestException(body);
    }

    private String getBody(Response response) {
        if (response == null || response.body() == null) {
            return null;
        }

        try {
            return Util.toString(response.body().asReader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
