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
        return criteria.list();
    }

    @Override
    public void saveTask(Task task) {
        save(task);
    }

    @Override
    public String existTask(String operateClass) {
        Criteria criteria = getSession().createCriteria(Task.class);
        criteria.setProjection(Projections.count("operateClass"));
        return criteria.uniqueResult().toString();
    }

    @Override
    public Task getTaskById(Integer id) {
        return getById(id);
    }
}
