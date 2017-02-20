package com.solverpeng.dao.base;

import com.solverpeng.beans.Task;
import com.solverpeng.orm.Page;
import com.solverpeng.utils.ReflectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author solverpeng
 * @create 2016-11-08-16:53
 */
public class BaseDao<T, PK> {
    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> entityClass;

    public BaseDao() {
        entityClass = ReflectionUtils.getSuperGenericType(getClass());
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void batchSave(List<T> entities) {
        for (int i = 0; i < entities.size(); i++) {
            getSession().save(entities.get(i));

            if ((i + 1) % 50 == 0) {
                getSession().flush();
                getSession().clear();
            }
        }
    }

    public T getById(Integer id) {
        return (T) getSession().get(Task.class, id);
    }

    //传入的 page 中包含了 pageNo 和 pageSize
    public Page<T> getPage(Page<T> page) {
        //1. 查询总的记录数
        long totalItemNumbers = getTotalItemNumbers();
        page.setTotalItemNumbers(totalItemNumbers);

        //2. 查询当前页的 List
        List<T> content = getContent(page);
        page.setContent(content);

        return page;
    }

    protected String buildContentHQL() {
        String hql = "FROM " + entityClass.getName() + " e";
        return hql;
    }

    protected List<T> getContent(Page<T> page) {
        Query query = getSession().createQuery(buildContentHQL());

        query.setFirstResult(page.getFirstResult());
        query.setMaxResults(page.getMaxResults());

        return query.list();
    }

    private long getTotalItemNumbers() {
        String hql = "SELECT count(e." + getIdName() + ") FROM " + entityClass.getName() + " e";
        Query query = getSession().createQuery(hql);
        return (long) query.uniqueResult();
    }

    //从 hibernate 中获取实体类对应的主键的名字.
    private String getIdName() {
        ClassMetadata cmd = this.sessionFactory.getClassMetadata(entityClass);
        return cmd.getIdentifierPropertyName();
    }

    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public Criteria getCriteria() {
        return getSession().createCriteria(entityClass);
    }

    //获取全部的对象, 但关联的对象使用懒加载的方式.
    //根据传入的可变参数来确实是否进行缓存
    public List<T> getAll(boolean... isCached) {
        Criteria criteria = getCriteria();

        if (isCached != null && isCached.length > 0) {
            criteria.setCacheable(isCached[0]);
        }

        return criteria.list();
    }

    public List<T> getByPropertyIsNull(String propertyName) {
        Criterion criterion = Restrictions.isNull(propertyName);

        Criteria criteria = getCriteria().add(criterion);
        return criteria.list();
    }

    public List<T> getByList(String propertyName, Object propertyValue) {
        Criterion criterion = Restrictions.eq(propertyName, propertyValue);
        Criteria criteria = getCriteria().add(criterion);

        return criteria.list();
    }

    /**
     * 使用 QBC 返回和属性名、属性值相等的 Employee 对象.
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    public T getBy(String propertyName, Object propertyValue) {
        Criterion criterion = Restrictions.eq(propertyName, propertyValue);
        Criteria criteria = getCriteria().add(criterion);
        return (T) criteria.uniqueResult();
    }


}
