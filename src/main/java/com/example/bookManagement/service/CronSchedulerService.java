package com.example.bookManagement.service;

import com.example.bookManagement.entity.CronScheduler;
import com.example.bookManagement.repository.CronSchedulerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class CronSchedulerService {

    private final CronSchedulerRepo cronSchedulerRepo;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> scheduledFuture;

    @Autowired
    public CronSchedulerService(CronSchedulerRepo cronSchedulerRepo) {
        this.cronSchedulerRepo = cronSchedulerRepo;
        threadPoolTaskScheduler.initialize();
    }

    public CronScheduler setCronScheduler(CronScheduler cronScheduler) {
        return cronSchedulerRepo.save(cronScheduler);
    }

    public List<CronScheduler> getCronScheduler() {
        return cronSchedulerRepo.findAll();
    }

    public CronScheduler updateCronScheduler(CronScheduler cronScheduler, Long id) {
        CronScheduler result = cronSchedulerRepo.findById(id)
                                                .orElse(new CronScheduler());
        result.setTaskName(cronScheduler.getTaskName());
        result.setCronExpression(cronScheduler.getCronExpression());
        CronScheduler save = cronSchedulerRepo.save(result);
//        restartSchedulerTask(cronScheduler.getCronExpression());
        return save;
    }

    // this method set the scheduler with task and trigger
//    private void restartSchedulerTask(String cronExpression) {
//        if (scheduledFuture != null){
//            scheduledFuture.cancel(false);
//        }
//        threadPoolTaskScheduler.schedule(updateSchedulerProcessor(), new CronTrigger(cronExpression));
//    }

    //    schedule the method to run
    @Scheduled(fixedRate = 10000)
    public void updateSchedulerProcessor() {
        log.info("scheduler processing");
        List<CronScheduler> cronScheduler = getCronScheduler();
        cronScheduler.forEach(entry -> {
                                  entry.setStatus("Completed");
                                  cronSchedulerRepo.save(entry);
                              }
        );
        log.info("total items processed : " + cronScheduler.size());
    }

}
