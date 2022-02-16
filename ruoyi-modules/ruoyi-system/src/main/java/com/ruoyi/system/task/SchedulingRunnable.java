package com.ruoyi.system.task;

import com.ruoyi.system.domain.SysHttpCollection;
import com.ruoyi.system.utils.HttpCollectionUtil;

public class SchedulingRunnable implements Runnable {


    private SysHttpCollection cron;
    private HttpCollectionUtil httpCollectionUtil;

    public SchedulingRunnable(SysHttpCollection cron, HttpCollectionUtil httpCollectionUtil) {
        this.cron = cron;
        this.httpCollectionUtil = httpCollectionUtil;
    }

    @Override
    public void run() {
        //执行任务
        // System.out.println("执行任务");
        httpCollectionUtil.execCollection(cron.getId());
    }
}
