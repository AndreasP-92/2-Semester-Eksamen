package com.example.wearegantt.model;

import java.util.List;

public class Project {
    private int project_id;
    private String project_name;
    private String project_desc;
    private int project_duration;
    private String project_start;
    private String project_end;
    private int fk_orgId;

//    lav en liste eller hashset


    public Project(int project_id, String project_name, String project_desc, int project_duration, String project_start, String project_end, int fk_orgId) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_desc = project_desc;
        this.project_duration = project_duration;
        this.project_start = project_start;
        this.project_end = project_end;
        this.fk_orgId = fk_orgId;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_desc() {
        return project_desc;
    }

    public void setProject_desc(String project_desc) {
        this.project_desc = project_desc;
    }

    public int getProject_duration() {
        return project_duration;
    }

    public void setProject_duration(int project_duration) {
        this.project_duration = project_duration;
    }

    public String getProject_start() {
        return project_start;
    }

    public void setProject_start(String project_start) {
        this.project_start = project_start;
    }

    public String getProject_end() {
        return project_end;
    }

    public void setProject_end(String project_end) {
        this.project_end = project_end;
    }

    public int getFk_orgId() {
        return fk_orgId;
    }

    public void setFk_orgId(int fk_orgId) {
        this.fk_orgId = fk_orgId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "project_id=" + project_id +
                ", project_name='" + project_name + '\'' +
                ", project_desc='" + project_desc + '\'' +
                ", project_duration=" + project_duration +
                ", project_start='" + project_start + '\'' +
                ", project_end='" + project_end + '\'' +
                ", fk_orgId=" + fk_orgId +
                '}';
    }
}
