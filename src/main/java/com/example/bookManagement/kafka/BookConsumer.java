package com.example.bookManagement.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookConsumer {
    private static final Logger logger = LoggerFactory.getLogger(BookConsumer.class);

    @KafkaListener(topics = "book-events",
                   groupId = "book-consumer-group")
    public void listen(String message) {
        logger.info("Received message: '{}' ", message);
    }

}
