package com.ruoyi.system.task;

import com.ruoyi.system.controller.SysHttpCollectionController;
import com.ruoyi.system.domain.SysHttpCollection;
import com.ruoyi.system.utils.HttpCollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleConfig implements CommandLineRunner {


    @Autowired
    private CronTaskRegistrar taskRegistrar;

    @Autowired
    private SysHttpCollectionController collectionController;

    @Autowired
    private HttpCollectionUtil httpCollectionUtil;

    @Override
    public void run(String... args) throws Exception {
        List<SysHttpCollection> crons = collectionController.getColList();
        for (SysHttpCollection cron : crons) {
            if (cron.getCronFlag() == 1) {
                SchedulingRunnable task = new SchedulingRunnable(cron, httpCollectionUtil);
                taskRegistrar.addCronTask(cron.getId(), task, cron.getCronParam());
            }
        }
    }

}
