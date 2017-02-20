package com.solverpeng.beans;

import com.solverpeng.utils.DateUtil;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class Task {
    private Integer id;
    private String taskName;
    private Date startTime;
    private Date endTime;
    private Integer intervalTime;
    private String intervalUnit;
    private String operateClass;
    private String cronExpression;
    private Date lastExecuteTime;
    private Integer status;
    private String remark;
    private Integer deleted;

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String FORMAT_START_TIME = DATE_FORMAT;
    public static final String FORMAT_END_TIME = DATE_FORMAT;
    public static final String FORMAT_LAST_EXEC_TIME = DATE_FORMAT;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(String intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public String getOperateClass() {
        return operateClass;
    }

    public void setOperateClass(String operateClass) {
        this.operateClass = operateClass;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getStartTimeString() {
        return DateUtil.dateToString(getStartTime(), FORMAT_START_TIME);
    }

    public void setStartTimeString(String value) {
        setStartTime(DateUtil.stringToDate(value,FORMAT_START_TIME));
    }

    public String getEndTimeString() {
        return DateUtil.dateToString(getEndTime(), FORMAT_END_TIME);
    }

    public void setEndTimeString(String value) {
        setEndTime(DateUtil.stringToDate(value,FORMAT_END_TIME));
    }

    public String getLastExecuteTimeString() {
        return DateUtil.dateToString(getLastExecuteTime(), FORMAT_LAST_EXEC_TIME);
    }

    public void setLastExecuteTimeString(String value) {
        setLastExecuteTime(DateUtil.stringToDate(value,FORMAT_LAST_EXEC_TIME));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (taskName != null ? !taskName.equals(task.taskName) : task.taskName != null) return false;
        if (startTime != null ? !startTime.equals(task.startTime) : task.startTime != null) return false;
        if (endTime != null ? !endTime.equals(task.endTime) : task.endTime != null) return false;
        if (intervalTime != null ? !intervalTime.equals(task.intervalTime) : task.intervalTime != null) return false;
        if (intervalUnit != null ? !intervalUnit.equals(task.intervalUnit) : task.intervalUnit != null) return false;
        if (operateClass != null ? !operateClass.equals(task.operateClass) : task.operateClass != null) return false;
        if (cronExpression != null ? !cronExpression.equals(task.cronExpression) : task.cronExpression != null)
            return false;
        if (lastExecuteTime != null ? !lastExecuteTime.equals(task.lastExecuteTime) : task.lastExecuteTime != null)
            return false;
        if (status != null ? !status.equals(task.status) : task.status != null) return false;
        if (remark != null ? !remark.equals(task.remark) : task.remark != null) return false;
        if (deleted != null ? !deleted.equals(task.deleted) : task.deleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (intervalTime != null ? intervalTime.hashCode() : 0);
        result = 31 * result + (intervalUnit != null ? intervalUnit.hashCode() : 0);
        result = 31 * result + (operateClass != null ? operateClass.hashCode() : 0);
        result = 31 * result + (cronExpression != null ? cronExpression.hashCode() : 0);
        result = 31 * result + (lastExecuteTime != null ? lastExecuteTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", intervalTime=" + intervalTime +
                ", intervalUnit='" + intervalUnit + '\'' +
                ", operateClass='" + operateClass + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", lastExecuteTime=" + lastExecuteTime +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
