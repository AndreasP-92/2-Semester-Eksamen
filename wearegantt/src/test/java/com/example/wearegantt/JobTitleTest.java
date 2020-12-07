package com.example.wearegantt;

import com.example.wearegantt.model.JobTitle;
import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.repository.JobTitleRepo;
import com.example.wearegantt.repository.OrganizationRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JobTitleTest {

    JobTitleRepo jobTitleRepo = new JobTitleRepo();

//   Virker!!!
    @Test
    public void getOneJobTitle(){
        JobTitle jobTitleObj = jobTitleRepo.getOneJobTitleWOrgId(6);

        System.out.println(jobTitleObj);
    }

//    Virker!!!!
    @Test
    public void getAllJobTitle(){
        List<JobTitle> jobTitleList = jobTitleRepo.getAllJobTitles();

        System.out.println(jobTitleList);
    }
//  Virker!!!
    @Test
    public void insertJobTitle(){

        jobTitleRepo.InsertJobTitle("manager", 6);

        System.out.println(jobTitleRepo);
    }



    @Test
    public void updateJobTitle(){
        jobTitleRepo.updateJobTitle("Jeffrey",1);

    }

}

