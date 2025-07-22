package com.example.bookManagement.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookProducer {

    private static final Logger logger = LoggerFactory.getLogger(BookProducer.class);
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    public BookProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String message) {
        logger.info("Sending message '{}' on topic '{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }

}
