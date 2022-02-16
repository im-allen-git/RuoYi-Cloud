package com.ruoyi.system.task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 添加定时任务注册类，用来增加、删除定时任务。
 *
 * @author 81509
 */
@Component
public class CronTaskRegistrar implements DisposableBean {

    private final Map<Long, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(20);

    @Autowired
    private TaskScheduler taskScheduler;


    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }

    public void addCronTask(Long cronId, Runnable task, String cronExpression) {
        addCronTask(cronId, new CronTask(task, cronExpression));
    }

    public void addCronTask(Long cronId, CronTask cronTask) {
        if (cronTask != null) {
            if (this.scheduledTasks.containsKey(cronId)) {
                removeCronTask(cronId);
            }
            this.scheduledTasks.put(cronId, scheduleCronTask(cronTask));
        }
    }

    public void removeCronTask(Long cronId) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(cronId);
        if (scheduledTask != null) {
            scheduledTask.cancel();
        }
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }


    @Override
    public void destroy() {
        for (ScheduledTask task : this.scheduledTasks.values()) {
            task.cancel();
        }
        this.scheduledTasks.clear();
    }
}
