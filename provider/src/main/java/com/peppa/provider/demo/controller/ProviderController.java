package com.peppa.provider.demo.controller;

import com.peppa.provider.demo.model.ProviderPOJO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
public class ProviderController {
    private final AtomicLong counter = new AtomicLong(new Random().nextInt(1000));

    @Value("${server.port}")
    private int port;

    @RequestMapping("/test")
    public ProviderPOJO test(@RequestParam(value = "name", defaultValue = "provider") String name) throws IOException, InterruptedException {
        // 当name值为io时抛出IOException异常，导致返回http状态码为5xx的响应，对consumer端hystrix来说就是失败
        if ("io".equalsIgnoreCase(name)) {
            throw new IOException("simulate io exception");
        }
        // 睡眠10秒，模拟provider处理时间
        Thread.sleep(10);

        ProviderPOJO providerPOJO = new ProviderPOJO(counter.incrementAndGet(), name, port);
        log.info("ProviderController:test(): " + providerPOJO);
        return providerPOJO;
    }
}
