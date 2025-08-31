package com.example.bookManagement.controller;

import com.example.bookManagement.entity.CronScheduler;
import com.example.bookManagement.service.CronSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    private final CronSchedulerService cronSchedulerService;

    @Autowired
    public SchedulerController(CronSchedulerService cronSchedulerService) {
        this.cronSchedulerService = cronSchedulerService;
    }

    @PostMapping("/setCronScheduler")
    public ResponseEntity<CronScheduler> setCronScheduler(@RequestBody CronScheduler cronScheduler) {
        CronScheduler result = cronSchedulerService.setCronScheduler(cronScheduler);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllCronScheduler")
    public ResponseEntity<List<CronScheduler>> getAllCronScheduler() {
        List<CronScheduler> result = cronSchedulerService.getCronScheduler();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateCronScheduler/{id}")
    public ResponseEntity<CronScheduler> updateCronScheduler(@RequestBody CronScheduler cronScheduler,
                                                             @PathVariable Long id) {
        CronScheduler result = cronSchedulerService.updateCronScheduler(cronScheduler, id);
        return ResponseEntity.ok(result);
    }
}