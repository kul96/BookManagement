package com.example.bookManagement.service;

import com.example.bookManagement.kafka.OrderCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PublishEventService {

    private final ApplicationEventPublisher publisher;

    public PublishEventService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public String createOrder() {
        publisher.publishEvent(new OrderCreatedEvent("id1"));
        return "Order created";
    }
}
