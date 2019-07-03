package com.peppa.serviceImpl;

import com.peppa.service.MessageService;

public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage() {
        return "hello world!";
    }
}
