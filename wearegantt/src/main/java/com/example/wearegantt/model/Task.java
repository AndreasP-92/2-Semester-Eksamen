package com.example.wearegantt.model;

import java.sql.Timestamp;

public class Task {
    private int task_id;
    private String task_name;
    private String task_duration;
    private String task_start;
    private String tak_end;

    public Task(int task_id, String task_name, String task_duration, String task_start, String tak_end) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_duration = task_duration;
        this.task_start = task_start;
        this.tak_end = tak_end;
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

    public String getTask_duration() {
        return task_duration;
    }

    public void setTask_duration(String task_duration) {
        this.task_duration = task_duration;
    }

    public String getTask_start() {
        return task_start;
    }

    public void setTask_start(String task_start) {
        this.task_start = task_start;
    }

    public String getTak_end() {
        return tak_end;
    }

    public void setTak_end(String tak_end) {
        this.tak_end = tak_end;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", task_name='" + task_name + '\'' +
                ", task_duration='" + task_duration + '\'' +
                ", task_start='" + task_start + '\'' +
                ", tak_end='" + tak_end + '\'' +
                '}';
    }
}
