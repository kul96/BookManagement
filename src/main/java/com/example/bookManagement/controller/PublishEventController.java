package com.example.bookManagement.controller;

import com.example.bookManagement.service.PublishEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class PublishEventController {

    private final PublishEventService service;

    public PublishEventController(PublishEventService service) {
        this.service = service;
    }

    @GetMapping("/createEvent")
    public ResponseEntity<String> createOrder() {
        String order = service.createOrder();
        return ResponseEntity.ok(order);
    }
}
