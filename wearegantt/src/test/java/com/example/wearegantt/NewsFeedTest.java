package com.example.wearegantt;

import com.example.wearegantt.repository.JobTitleRepo;
import com.example.wearegantt.repository.NewsfeedRepo;
import org.junit.jupiter.api.Test;

public class NewsFeedTest {

    NewsfeedRepo newsfeedRepo = new NewsfeedRepo();

    JobTitleRepo jobTitleRepo = new JobTitleRepo();


    @Test
    public void getAllNewsWhere(){
        System.out.println(newsfeedRepo.getAllNews("test"));


    }

    @Test
    public void getAllJobTitlesWHere(){
        System.out.println(jobTitleRepo.getOneJobTitleWName("manager"));
    }
}
