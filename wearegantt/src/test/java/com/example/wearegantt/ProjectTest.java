package com.example.wearegantt;

import com.example.wearegantt.model.JobTitle;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.repository.ProjectRepo;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProjectTest {

    ProjectRepo projectRepo = new ProjectRepo();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSSSSS");

    private static final SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH.mm");

    @Test
    public void timeStampTest(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        System.out.println(timestamp);


//        System.out.println(sdf.format(timestamp));
        System.out.println(date.format(timestamp));
        System.out.println(time.format(timestamp));
    }

// GET ONE PROJECT
    @Test
    public void getAllProjects(){
        List<Project> projectList = projectRepo.getAllProjects();

        System.out.println(projectList);
    }

// GET ONE PROJECT
    @Test
    public void getOneProject(){
        Project projectObj = projectRepo.getOneProject(1);

        System.out.println(projectObj);
    }
// INSERT ONE PROJECT

    @Test
    public void InsertOneProject(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = sdf.format(timestamp);
        System.out.println(date);

//        System.out.println(timestamp);
        projectRepo.InsertProject("name3","desc3", date, date, date, 1, 1, 1);

    }
// INSERT JOB
    @Test
    public void insertJob(){
        projectRepo.insertJobTitle("job title test");
    }
//  GET ALL JOBS
    @Test
    public void getAllJobTitles(){
        List<JobTitle> jobList = projectRepo.getAllJobTitles();

        System.out.println(jobList);
    }
//    GET ONE JOB
    @Test
    public void getOneJobTitle(){
        JobTitle jobObj = projectRepo.getOneJobTitle(1);

        System.out.println(jobObj);
    }
//  UPDATE JOB
    @Test
    public void updateJob(){
        projectRepo.updateJobTitle(1,"Test Job Title");

    }
}
