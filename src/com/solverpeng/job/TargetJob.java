package com.solverpeng.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class TargetJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时要执行的任务1....");
    }
}
