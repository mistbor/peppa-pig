package com.peppa.consumer.controller;

import com.peppa.consumer.model.ConsumerPOJO;
import com.peppa.consumer.model.ProviderPOJO;
import com.peppa.consumer.service.ProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
public class ConsumerController {
    private final AtomicLong counter = new AtomicLong(new Random().nextInt(1000));

    @Value("${server.port}")
    private int port;

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ConsumerPOJO test(@RequestParam(value = "name", defaultValue = "consumer") String name) {
        ProviderPOJO providerPOJO = providerService.test(name);
        ConsumerPOJO consumerPOJO = new ConsumerPOJO(counter.incrementAndGet(), name, port, providerPOJO);
        return consumerPOJO;
    }
}
