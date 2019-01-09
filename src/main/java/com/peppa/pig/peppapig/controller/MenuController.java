package com.peppa.pig.peppapig.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(value = "menu 相关接口", description = "menu 相关接口")
public class MenuController {

    @RequestMapping(method = RequestMethod.GET, value = "/just/test")
    public String test(@RequestParam("name") String name) {
        log.info("test name" + name);
        return "hello, " + name;
    }
}