package com.solverpeng.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.solverpeng.beans.Task;
import com.solverpeng.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by solverpeng on 2017/2/13 0013.
 */
@Controller
@Scope(value = "prototype")
public class TaskAction extends ActionSupport implements ModelDriven<Task>, Preparable, RequestAware{
    private static final String LIST_SUCCESS = "list-success";
    private static final String CREATE_SUCCESS = "create-success";
    private static final String SHOW_SUCCESS = "show-success";

    private Map<String, Object> request;

    private Task task;
    private Integer id;

    public void setId(String id) {
        try {
            this.id = Integer.parseInt(id);
        } catch (NumberFormatException e) {
        }
    }

    @Autowired
    private TaskService taskService;

    @Override
    public void prepare() throws Exception {
    }

    @Override
    public Task getModel() {
        return this.task;
    }

    public void prepareList() {
        this.task = new Task();
    }

    public String list() {
        List taskList = taskService.listTask(task);
        request.put("taskList", taskList);
        return LIST_SUCCESS;
    }

    public String create() {
        return CREATE_SUCCESS;
    }

    public void prepareSave() {
        if (id != null) {
            this.task = taskService.getTaskById(id);
        } else {
            this.task = new Task();
        }
    }

    public String save() {
        String result = "1";
        try {
            String operateClass = task.getOperateClass();

            try {
                Class.forName(operateClass);
            } catch (Exception e) {
                e.printStackTrace();
                result = "不存在[" + operateClass + "]类！";
            }

            String count = taskService.existTask(task.getTaskName());

            if(StringUtils.isBlank(count) || !"0".equals(count)) {
                result = "该任务已经存在!";
            } else {
                taskService.saveOrUpdateTask(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "0";
        }

        return writeAjaxResponse(result);
    }

    public void prepareEdit() {
        this.task = taskService.getTaskById(id);
    }

    public String edit() {
        return CREATE_SUCCESS;
    }

    public void prepareShow() {
        this.task = taskService.getTaskById(id);
    }

    public String show() {
        return SHOW_SUCCESS;
    }

    private String writeAjaxResponse(String ajaxString){
        try {
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            PrintWriter out = ServletActionContext.getResponse().getWriter();
            out.write(ajaxString);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            return null;
        }
    }

    @Override
    public void setRequest(Map<String, Object> map) {
        this.request = map;
    }
}
