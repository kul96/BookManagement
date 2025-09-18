package com.example.bookManagement.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

// access value from properties file
    @Value("${app.event.order.value}")
    String orderValue;

    @Autowired
    Environment env;

    @org.springframework.context.event.EventListener
//    @Order(2)
//    @Async
    public void eventListener1(OrderCreatedEvent event) throws InterruptedException {
        System.out.println("Event listener 1 : " + event.getOrderId());
        Thread.sleep(3000);
        System.out.println("Event listener 1 completed " + orderValue);
//        System.out.println("Event listener 1 completed " + env.getProperty("app.env.value"));
    }

    @org.springframework.context.event.EventListener
//    @Order(1)
//    @Async
    public void eventListener2(OrderCreatedEvent event) throws InterruptedException {
        System.out.println("Event listener 2 : " + event.getOrderId());
        Thread.sleep(1000);
        System.out.println("Event listener 2 completed " + env.getProperty("app.env.value"));
    }

    // Condition listener
    @org.springframework.context.event.EventListener(condition = "#event.amount>100")
    public void eventListener3(OrderCreatedEvent event)  {
        System.out.println("/n/nCondition Event listener 3 : " + event.getAmount());
    }
}
