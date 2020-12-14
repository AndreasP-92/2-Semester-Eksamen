package com.example.wearegantt;


import com.example.wearegantt.model.Profile;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.UserRepo;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

public class ProfileTest {

    UserRepo userRepo = new UserRepo();
    ProfileRepo profileRepo = new ProfileRepo();


    @Test
    void insertUser() {
//        userRepo.insertUser("mail2","pass2",1);
    }

    @Test
    public void getAllProfiles() {
        List<Profile> profilesList = profileRepo.getAllProfiles();

        System.out.println(profilesList);
    }

    // GET ONE PROFILE
    @Test
    public void getOneProfile() {
        Profile profileObj = profileRepo.getOneProfile(6);

        System.out.println(profileObj);
    }
    // INSERT ONE PROFILE

    @Test
    public void InsertOneProfile() {

        profileRepo.insertProfile("name3", "desc3", "valby", 666, "dk", 2500, "stud", 6);
    }

    // UPDATE ONE PROJECT
    @Test
    public void updateProject(){
        profileRepo.updateProfile(6, "name3", "desc3", "valby", 666, "dk", 2500, "stud", 6);

    }

}