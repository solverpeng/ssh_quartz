package com.solverpeng.service.impl;

import com.solverpeng.beans.Task;
import com.solverpeng.dao.TaskDao;
import com.solverpeng.service.TaskService;
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

    @Transactional(readOnly = true)
    @Override
    public List listTask(Task task) {
        return taskDao.listTask(task);
    }

    @Transactional
    @Override
    public void saveOrUpdateTask(Task task) {
        taskDao.saveOrUpdateTask(task);
        // 执行任务
        /*if(CronJob.isRun()){
            if(ttaskInfo.getStatus().equals("1")){
                CronJob.getInstance().addJob(ttaskInfo);
            }
        }*/
    }

    @Transactional(readOnly = true)
    @Override
    public String existTask(String taskName) {
        return taskDao.existTask(taskName);
    }

    @Transactional(readOnly = true)
    @Override
    public Task getTaskById(Integer id) {
        return taskDao.getTaskById(id);
    }
}
