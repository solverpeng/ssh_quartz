package com.solverpeng.test;

import com.solverpeng.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-*.xml")
public class TestTask {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TaskService taskService;

    @Test
    public void testTaskService() {
    }

    @Test
    public void testDataSource() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }


}
