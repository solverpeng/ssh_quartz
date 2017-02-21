package com.solverpeng.service.impl;

import com.solverpeng.beans.Task;
import com.solverpeng.dao.TaskDao;
import com.solverpeng.service.TaskService;
import com.solverpeng.utils.SimpleJob;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by solverpeng on 2017/2/13 0013.
 */
@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskDao taskDao;

    @Override
    @Transactional(readOnly = true)
    public List listTask(Task task) {
        return taskDao.listTask(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateTask(Task task) throws Exception {
        taskDao.saveOrUpdateTask(task);
        restartScheduler();
    }

    @Override
    @Transactional(readOnly = true)
    public String existTask(String taskName) {
        return taskDao.existTask(taskName);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Integer id) {
        return taskDao.getTaskById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTaskList() {
        return taskDao.getTaskList();
    }

    @Override
    @Transactional
    public int removeTask(Integer id) {
        int returnCode = 0;
        // 需要先将任务从 Scheduler 中删除，然后再去库中删除，删除后重启 Scheduler
        Task task = taskDao.getTaskById(id);
        try {
            SimpleJob.removeJob(task);
            taskDao.removeJob(task);
            restartScheduler();
            returnCode = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnCode;
    }

    private void restartScheduler() throws SchedulerException {
        // 需要先暂停，后执行
        if (!SimpleJob.isStop()) {
            SimpleJob.stop();
        }
        if (SimpleJob.isStop() || !SimpleJob.isRun()) {
            SimpleJob.begin();
        }
    }
}
