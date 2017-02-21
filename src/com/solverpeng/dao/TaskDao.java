package com.solverpeng.dao;

import com.solverpeng.beans.Task;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public interface TaskDao {
    List listTask(Task task);

    String existTask(String taskName);

    Task getTaskById(Integer id);

    void saveOrUpdateTask(Task task);

    List<Task> getTaskList();

}
