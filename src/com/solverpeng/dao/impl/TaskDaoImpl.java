package com.solverpeng.dao.impl;

import com.solverpeng.beans.Task;
import com.solverpeng.dao.TaskDao;
import com.solverpeng.dao.base.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
@Repository
public class TaskDaoImpl extends BaseDao<Task, Integer> implements TaskDao {
    @Override
    public List listTask(Task task) {
        Criteria criteria = getSession().createCriteria(Task.class);
        if (StringUtils.isNotBlank(task.getTaskName())) {
            criteria.add(Restrictions.like("taskName", task.getTaskName(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(task.getOperateClass())) {
            criteria.add(Restrictions.like("operateClass", task.getOperateClass(), MatchMode.ANYWHERE));
        }
        if (Objects.equals(1, task.getStatus())) {
            criteria.add(Restrictions.eq("status", task.getStatus()));
        }
        criteria.add(Restrictions.eq("deleted", 0));
        return criteria.list();
    }

    @Override
    public String existTask(String taskName) {
        Criteria criteria = getSession().createCriteria(Task.class);
        criteria.add(Restrictions.eq("taskName", taskName));
        criteria.setProjection(Projections.count("taskName"));
        return criteria.uniqueResult().toString();
    }

    @Override
    public Task getTaskById(Integer id) {
        return getById(id);
    }

    @Override
    public void saveOrUpdateTask(Task task) {
        save(task);
    }

    @Override
    public List<Task> getTaskList() {
        return getByList("deleted", 0);
    }

    @Override
    public void removeJob(Task task) {
        task.setDeleted(1);
        getSession().update(task);
    }

}
