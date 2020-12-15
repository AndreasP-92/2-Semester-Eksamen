package com.example.wearegantt.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Task {
    private int task_id;
    private String task_name;
    private String task_desc;
    private int task_duration;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date task_start;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  task_end;
    private int task_processEnd;
    private int task_processStart;
    private String fk_projectName;
    private String fk_profileName;
    private String fk_ganttPhaseName;
    private String fk_jobTitleName;

    public Task(int task_id, String task_name, String task_desc, int task_duration, Date task_start, Date task_end, int task_processEnd, int task_processStart, String fk_projectName, String fk_profileName, String fk_ganttPhaseName, String fk_jobTitleName) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.task_duration = task_duration;
        this.task_start = task_start;
        this.task_end = task_end;
        this.task_processEnd = task_processEnd;
        this.task_processStart = task_processStart;
        this.fk_projectName = fk_projectName;
        this.fk_profileName = fk_profileName;
        this.fk_ganttPhaseName = fk_ganttPhaseName;
        this.fk_jobTitleName = fk_jobTitleName;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public int getTask_duration() {
        return task_duration;
    }

    public void setTask_duration(int task_duration) {
        this.task_duration = task_duration;
    }

    public Date getTask_start() {
        return task_start;
    }

    public void setTask_start(Date task_start) {
        this.task_start = task_start;
    }

    public Date getTask_end() {
        return task_end;
    }

    public void Date(Date task_end) {
        this.task_end = task_end;
    }

    public int getTask_processEnd() {
        return task_processEnd;
    }

    public void setTask_processEnd(int task_processEnd) {
        this.task_processEnd = task_processEnd;
    }

    public int getTask_processStart() {
        return task_processStart;
    }

    public void setTask_processStart(int task_processStart) {
        this.task_processStart = task_processStart;
    }

    public String getFk_projectName() {
        return fk_projectName;
    }

    public void setFk_projectName(String fk_projectName) {
        this.fk_projectName = fk_projectName;
    }

    public String getFk_profileName() {
        return fk_profileName;
    }

    public void setFk_profileName(String fk_profileName) {
        this.fk_profileName = fk_profileName;
    }

    public String getFk_ganttPhaseName() {
        return fk_ganttPhaseName;
    }

    public void setFk_ganttPhaseName(String fk_ganttPhaseName) {
        this.fk_ganttPhaseName = fk_ganttPhaseName;
    }

    public String getFk_jobTitleName() {
        return fk_jobTitleName;
    }

    public void setFk_jobTitleName(String fk_jobTitleName) {
        this.fk_jobTitleName = fk_jobTitleName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", task_name='" + task_name + '\'' +
                ", task_desc='" + task_desc + '\'' +
                ", task_duration=" + task_duration +
                ", task_start=" + task_start +
                ", task_end=" + task_end +
                ", task_processEnd=" + task_processEnd +
                ", task_processStart=" + task_processStart +
                ", fk_projectName='" + fk_projectName + '\'' +
                ", fk_profileName='" + fk_profileName + '\'' +
                ", fk_ganttPhaseName='" + fk_ganttPhaseName + '\'' +
                ", fk_jobTitleName='" + fk_jobTitleName + '\'' +
                '}';
    }
}
