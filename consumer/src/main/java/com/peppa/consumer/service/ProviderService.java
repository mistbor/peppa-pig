package com.peppa.consumer.service;

import com.peppa.consumer.model.ProviderPOJO;
import com.peppa.consumer.servicefallback.ProviderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 该注解启用feign来实现对provider微服务的调用
 */
@FeignClient(name = "provider", fallback = ProviderFallback.class)
@Component
public interface ProviderService {

    /**
     * provider接口
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET, consumes = "application/json")
    ProviderPOJO test(@RequestParam("name") String name);
}
