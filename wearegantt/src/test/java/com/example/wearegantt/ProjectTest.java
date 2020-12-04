package com.example.wearegantt;

import com.example.wearegantt.model.JobTitle;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.repository.ProjectRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProjectTest {

    ProjectRepo projectRepo = new ProjectRepo();

//
//    @Test
//    public void getOneProject(){
//        Project projectObj = projectRepo.getOneProject(1);
//
//        System.out.println(projectObj);
//
//
//    }



//    @Test
//    public void InsertOneProject(){
//        Project projectObj = projectRepo.InsertProject(1);
//
//        System.out.println(projectObj);
//    }

    @Test
    public void insertJob(){
        projectRepo.insertJobTitle("job title test");
    }

    @Test
    public void getAllJobTitles(){
        List<JobTitle> jobList = projectRepo.getAllJobTitles();

        System.out.println(jobList);
    }
    @Test
    public void getOneJobTitle(){
        JobTitle jobObj = projectRepo.getOneJobTitle(1);

        System.out.println(jobObj);
    }

    @Test
    public void updateOrg(){
        projectRepo.updateJobTitle(1,"Test Job Title");

    }
}
