package com.solverpeng.utils;

import com.solverpeng.beans.Task;
import com.solverpeng.service.TaskService;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Calendar;
import java.util.List;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SimpleJob {
    private static final Logger logger = Logger.getLogger(SimpleJob.class);
    private static final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static Scheduler scheduler;
    private static ApplicationContext applicationContext;

    static {
        ServletContext servletContext = ServletActionContext.getServletContext();
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动所有的定时任务
     */
    public static void begin() throws SchedulerException {
        scheduler.clear();
        TaskService taskService = applicationContext.getBean(TaskService.class);
        List<Task> taskList = taskService.getTaskList();
        logger.info("开始加载任务!");
        for (Task task : taskList) {
            Class clazz;
            String operateClass = task.getOperateClass();
            try {
                clazz = Class.forName(operateClass);
                JobDetailImpl jobDetail = new JobDetailImpl();
                jobDetail.setJobClass(clazz);
                jobDetail.setName(operateClass);
                Trigger trigger = getTrigger(task);
                scheduler.scheduleJob(jobDetail, trigger);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error("加载失败!没有发现任务类：" + operateClass);
            }
        }
        logger.info("加载任务完毕!");
        logger.info("启动定时器!");
        scheduler.start();
    }

    /**
     * 停止定时任务调度器
     */
    public static void stop() throws SchedulerException {
        scheduler.standby();
        if (scheduler.isInStandbyMode()) {
            logger.info("定时器关闭成功!");
        } else {
            logger.info("定时器关闭失败!");
        }
    }

    /**
     * 判断当前调度器状态
     */
    public static boolean isRun() throws SchedulerException {
        return scheduler.isStarted();
    }

    public static boolean isStop() throws SchedulerException {
        return scheduler.isInStandbyMode();
    }

    public static boolean exist(Task task) throws SchedulerException {
        return scheduler.checkExists(new JobKey(task.getOperateClass()));
    }

    /**
     * 添加定时任务
     */
    public void addJob(Task task) throws Exception {
        logger.info("添加定时任务:[" + task.getTaskName() + "]");
        String operateClass = task.getOperateClass();
        Class clazz = Class.forName(operateClass);
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setJobClass(clazz);
        jobDetail.setName(operateClass);
        Trigger trigger = getTrigger(task);
        scheduler.scheduleJob(jobDetail, trigger);
        logger.info("添加定时任务:[" + task.getTaskName() + "]成功!");
    }

    /**
     * 删除指定定时任务
     */
    public void removeJob(Task task) throws Exception {
        logger.info("移除定时任务:[" + task.getTaskName() + "]");
        JobKey key = new JobKey(task.getOperateClass());
        scheduler.deleteJob(key);
        logger.info("移除定时任务:[" + task.getTaskName() + "}成功!");
    }

    /**
     * 获取定时任务触发器
     */
    private static Trigger getTrigger(Task task) {
        ScheduleBuilder<CalendarIntervalTrigger> scheduleBuilder = calendarIntervalSchedule();
        Calendar now = Calendar.getInstance();
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(task.getStartTime());

        switch (task.getIntervalUnit().charAt(0)) {
            case 'S': {
                scheduleBuilder = calendarIntervalSchedule().withIntervalInSeconds(task.getIntervalTime());
                if (now.after(startTime)) {
                    startTime.setTime(DateBuilder.futureDate(2, IntervalUnit.SECOND));
                }
            }
            break;
            case 'm': {
                scheduleBuilder = calendarIntervalSchedule().withIntervalInMinutes(task.getIntervalTime());
                if (now.after(startTime)) {
                    startTime.setTime(DateBuilder.futureDate(3, IntervalUnit.SECOND));
                }
            }
            break;
            case 'H': {
                scheduleBuilder = calendarIntervalSchedule().withIntervalInHours(task.getIntervalTime());
                if (now.after(startTime)) {
                    startTime = futureDate(startTime, task.getIntervalTime(), Calendar.HOUR_OF_DAY);
                }
            }
            break;
            case 'D': {
                scheduleBuilder = calendarIntervalSchedule().withIntervalInDays(task.getIntervalTime());
                if (now.after(startTime)) {
                    startTime = futureDate(startTime, task.getIntervalTime(), Calendar.DATE);
                }
            }
            break;
            case 'W': {
                scheduleBuilder = calendarIntervalSchedule().withIntervalInWeeks(task.getIntervalTime());
                if (now.after(startTime)) {
                    startTime = futureDate(startTime, task.getIntervalTime(), Calendar.WEEK_OF_YEAR);
                }
            }
            break;
            case 'M': {
                scheduleBuilder = calendarIntervalSchedule().withIntervalInMonths(task.getIntervalTime());
                if (now.after(startTime)) {
                    startTime = futureDate(startTime, task.getIntervalTime(), Calendar.MONTH);
                }
            }
            break;
            case 'Y': {
                scheduleBuilder = calendarIntervalSchedule().withIntervalInYears(task.getIntervalTime());
                if (now.after(startTime)) {
                    startTime = futureDate(startTime, task.getIntervalTime(), Calendar.YEAR);
                }
            }
            break;
        }
        if (task.getEndTime() != null) {
            return newTrigger().startAt(startTime.getTime()).withIdentity(task.getOperateClass()).endAt(task.getEndTime()).withSchedule(scheduleBuilder).build();
        }

        return newTrigger().startAt(startTime.getTime()).withIdentity(task.getOperateClass()).withSchedule(scheduleBuilder).build();
    }

    /**
     * 计算出下一次要执行的时间
     */
    private static Calendar futureDate(Calendar calendar, int interval, int unit) {
        Calendar temp = Calendar.getInstance();
        temp.setTime(DateUtil.stringToDate("19700101080000000", "yyyyMMddHHmmssSSS"));
        temp.setLenient(true);
        temp.set(unit, temp.get(unit) + interval);
        Long inl = temp.getTime().getTime();//计算出时间间隔的long值
        temp = Calendar.getInstance();
        //计算出所设置的开始时间到现在相对于设置的间隔时间的次数
        int times = (int) ((temp.getTimeInMillis() - calendar.getTimeInMillis()) / inl);
        //最近的一次执行时间到现在相差的时间差(long值)
        Long residue = (temp.getTimeInMillis() - calendar.getTimeInMillis()) % inl;
        //当最近的一次执行时间到现在相差的时间差大于10分钟时，开始时间设置为下次执行的时间
        if (residue > 1000 * 60 * 10) {
            times++;
        }
        calendar.setLenient(true);
        calendar.set(unit, calendar.get(unit) + times * interval);
        return calendar;
    }

}