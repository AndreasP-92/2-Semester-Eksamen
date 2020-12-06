package com.example.wearegantt.model;

public class GetProjectJobTitles {
    int projectJobTitle_id;
    int porject_id;
    String jobTitle_name;

    public GetProjectJobTitles(int projectJobTitle_id, int porject_id, String jobTitle_name) {
        this.projectJobTitle_id = projectJobTitle_id;
        this.porject_id = porject_id;
        this.jobTitle_name = jobTitle_name;
    }

    public int getProjectJobTitle_id() {
        return projectJobTitle_id;
    }

    public void setProjectJobTitle_id(int projectJobTitle_id) {
        this.projectJobTitle_id = projectJobTitle_id;
    }

    public int getPorject_id() {
        return porject_id;
    }

    public void setPorject_id(int porject_id) {
        this.porject_id = porject_id;
    }

    public String getJobTitle_name() {
        return jobTitle_name;
    }

    public void setJobTitle_name(String jobTitle_name) {
        this.jobTitle_name = jobTitle_name;
    }

    @Override
    public String toString() {
        return "ProjectJobTitles{" +
                "projectJobTitle_id=" + projectJobTitle_id +
                ", porject_id=" + porject_id +
                ", jobTitle_name='" + jobTitle_name + '\'' +
                '}';
    }
}
