package com.solverpeng.service;

import com.solverpeng.beans.Task;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public interface TaskService {
    List listTask(Task task);

    void saveOrUpdateTask(Task task);

    String existTask(String taskName);

    Task getTaskById(Integer id);

    List<Task> getTaskList();
}
