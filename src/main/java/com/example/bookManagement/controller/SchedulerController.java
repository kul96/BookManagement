package com.example.bookManagement.controller;

import com.example.bookManagement.entity.CronScheduler;
import com.example.bookManagement.service.CronSchedulerService;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<CronScheduler> setCronScheduler(@RequestBody CronScheduler cronScheduler) {
        CronScheduler result = cronSchedulerService.setCronScheduler(cronScheduler);
        return ResponseEntity.ok(result);
    }

    // "hasRole('USER')" or "hasAuthority('USER_WRITE')" or "hasAnyRole('USER','ADMIN')"
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/getAllCronScheduler")
    public ResponseEntity<List<CronScheduler>> getAllCronScheduler() {
        List<CronScheduler> result = cronSchedulerService.getCronScheduler();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateCronScheduler/{id}")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<CronScheduler> updateCronScheduler(@RequestBody CronScheduler cronScheduler,
                                                             @PathVariable Long id) {
        CronScheduler result = cronSchedulerService.updateCronScheduler(cronScheduler, id);
        return ResponseEntity.ok(result);
    }
}