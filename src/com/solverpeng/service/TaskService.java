package com.solverpeng.service;

import com.solverpeng.beans.Task;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public interface TaskService {
    List listTask(Task task);

    void saveOrUpdateTask(Task task) throws SchedulerException, Exception;

    String existTask(String taskName);

    Task getTaskById(Integer id);

    List<Task> getTaskList();

    int removeTask(Integer id);
}
