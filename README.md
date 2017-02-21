# ssh_quartz


## Quartz
1. 通过`org.quartz.SchedulerFactory.getScheduler()`获取到的`Scheduler`是单例的。
2. `org.quartz.Scheduler.start()` 可以定义在加载任务之前，也可以定义在加载任务之后。
3. 一个`Scheduler`可以添加多个`scheduleJob`。
如：
```java
public class BasicQuartzTest {

    public static void main(String[] args) throws SchedulerException {
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        Scheduler scheduler2 = stdSchedulerFactory.getScheduler();
        System.out.println(Objects.equals(scheduler, scheduler2));//true
        scheduler.start();
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setJobClass(MyJob.class);
        jobDetail.setName("jobDetail-name");

        JobDetailImpl jobDetail2 = new JobDetailImpl();
        jobDetail2.setJobClass(MyJob2.class);
        jobDetail2.setName("jobDetail-name2");

        SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        trigger.setStartTime(new Date());
        trigger.setRepeatCount(5);
        trigger.setRepeatInterval(1000);
        trigger.setName("trigger-name");

        SimpleTriggerImpl trigger2 = new SimpleTriggerImpl();
        trigger2.setStartTime(new Date());
        trigger2.setRepeatCount(5);
        trigger2.setRepeatInterval(2000);
        trigger2.setName("trigger-name2");

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(jobDetail2, trigger2);
        //scheduler.start();
    }
}
```

## SimpleJob
难点：
`Scheduler`的运行状态判断，start() 用来开启一个定时任务，而 shutdown() 是用来杀死一个定时任务，且不能再复活（即不可以对同一个 Scheduler 
调用 start()）。虽然存在 pauseAll() 和 resumeAll() 等方法，但是并没有提供相对应的 API 来判断是否在对应的状态里。
只提供了三个判断状态的方法：isStarted()/isShutdown()/isInStandbyMode()。通过测试发现，虽然调用了 shutdown() 方法，
但是 isStarted() 返回值还为 `true`，且调用 start() 方法的时候会抛出一个异常，当前 Scheduler 对象已被 shutdown。

最终使用了 isInStandbyMode()/standby() 组合来进行的状态判断。
说明：
默认情况下，isInStandbyMode() 返回 true，当调用 start() 方法后，会解除这种状态，即 isInStandbyMode() 返回 false。
调用 standby() 会临时的暂停 Scheduler 作用的 Trigger，即在调用 standby() 后再调用 isInStandbyMode() 返回 true。

因为我的任务是从数据库中读取的，所以每次都会向 Scheduler 中添加相应的任务。这里会存在一个问题，从库中查出来的任务和当前
Scheduler 中任务大部分是重合的，会提示已经有重复的任务在 Scheduler 中，所以在每次执行之前，都需要先将 Scheduler 中的
任务进行清空，然后再添加。