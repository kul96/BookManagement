package com.example.bookManagement.kafka;

import org.springframework.stereotype.Component;

@Component
public class EventListener {

    @org.springframework.context.event.EventListener
    public void eventListener(OrderCreatedEvent event) {
        System.out.println("Event listener" + event.getOrderId());
    }
}
