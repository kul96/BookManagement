package com.example.bookManagement.kafka;

import com.example.bookManagement.dto.BookDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookConsumer {
    private static final Logger logger = LoggerFactory.getLogger(BookConsumer.class);

    @KafkaListener(topics = "book-events",
                   groupId = "book-consumer-group1")
    public void listen(BookDTO message) {
        logger.info("Received message: '{}' ", message);
    }

}
