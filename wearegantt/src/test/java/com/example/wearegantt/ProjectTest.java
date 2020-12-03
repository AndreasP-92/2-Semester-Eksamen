package com.example.wearegantt;

import com.example.wearegantt.model.Project;
import com.example.wearegantt.repository.ProjectRepo;
import org.junit.jupiter.api.Test;

public class ProjectTest {

    ProjectRepo projectRepo = new ProjectRepo();


    @Test
    public void getOneProject(){
        Project projectObj = projectRepo.getOneProject(1);

        System.out.println(projectObj);


    }

    @Test
    public void InsertOneProject(){
        Project projectObj = projectRepo.InsertProject(1);

        System.out.println(projectObj);
    }
}
