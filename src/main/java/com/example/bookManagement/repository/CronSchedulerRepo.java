package com.example.bookManagement.repository;

import com.example.bookManagement.entity.CronScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronSchedulerRepo extends JpaRepository<CronScheduler, Long> {
}
