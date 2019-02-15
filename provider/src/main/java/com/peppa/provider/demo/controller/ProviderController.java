package com.peppa.provider.demo.controller;

import com.peppa.provider.demo.model.ProviderPOJO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
public class ProviderController {
    private final AtomicLong counter = new AtomicLong(new Random().nextInt(1000));

    @Value("${server.port}")
    private int port;

    @RequestMapping("/test")
    public ProviderPOJO test(@RequestParam(value = "name", defaultValue = "provider") String name) {
        ProviderPOJO providerPOJO = new ProviderPOJO(counter.incrementAndGet(), name, port);
        log.info("ProviderController:test(): " + providerPOJO);
        return providerPOJO;
    }
}
