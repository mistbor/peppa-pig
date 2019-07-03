package com;

import com.peppa.service.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        System.out.println("start success");
        MessageService messageService = context.getBean(MessageService.class);
        System.out.println("will print hello world");
        System.out.println(messageService.getMessage());
    }
}
