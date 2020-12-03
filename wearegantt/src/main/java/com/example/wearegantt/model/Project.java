package com.example.wearegantt.model;

public class Project {
    private int project_id;
    private String project_name;
    private String project_desc;
    private String project_duration;
    private String project_start;
    private String project_end;
    private int fk_orgId;
    private int fk_taskId;
    private int fk_jobTitleId;

    public Project(int anInt, String project_name, String project_desc, String project_duration, String project_start, String project_end, int fk_orgId, int fk_taskId, int fk_jobTitleId) {
        this.project_name = project_name;
        this.project_desc = project_desc;
        this.project_duration = project_duration;
        this.project_start = project_start;
        this.project_end = project_end;
        this.fk_orgId = fk_orgId;
        this.fk_taskId = fk_taskId;
        this.fk_jobTitleId = fk_jobTitleId;
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

    public String getProject_duration() {
        return project_duration;
    }

    public void setProject_duration(String project_duration) {
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

    public int getFk_taskId() {
        return fk_taskId;
    }

    public void setFk_taskId(int fk_taskId) {
        this.fk_taskId = fk_taskId;
    }

    public int getFk_jobTitleId() {
        return fk_jobTitleId;
    }

    public void setFk_jobTitleId(int fk_jobTitleId) {
        this.fk_jobTitleId = fk_jobTitleId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "project_id=" + project_id +
                ", project_name='" + project_name + '\'' +
                ", project_desc='" + project_desc + '\'' +
                ", project_duration='" + project_duration + '\'' +
                ", project_start='" + project_start + '\'' +
                ", project_end='" + project_end + '\'' +
                ", fk_orgId=" + fk_orgId +
                ", fk_taskId=" + fk_taskId +
                ", fk_jobTitleId=" + fk_jobTitleId +
                '}';
    }
}
