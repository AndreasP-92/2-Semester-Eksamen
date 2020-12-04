package com.example.wearegantt.model;

public class JobTitle {
    int jobTitle_Id;
    String jobTitle_name;

    public JobTitle(int jobTitle_Id, String jobTitle_name) {
        this.jobTitle_Id = jobTitle_Id;
        this.jobTitle_name = jobTitle_name;
    }

    public int getJobTitle_Id() {
        return jobTitle_Id;
    }

    public void setJobTitle_Id(int jobTitle_Id) {
        this.jobTitle_Id = jobTitle_Id;
    }

    public String getJobTitle_name() {
        return jobTitle_name;
    }

    public void setJobTitle_name(String jobTitle_name) {
        this.jobTitle_name = jobTitle_name;
    }

    @Override
    public String toString() {
        return "JobTitle{" +
                "jobTitle_Id=" + jobTitle_Id +
                ", jobTitle_name='" + jobTitle_name + '\'' +
                '}';
    }
}
