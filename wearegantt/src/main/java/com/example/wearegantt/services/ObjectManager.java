package com.example.wearegantt.services;

import com.example.wearegantt.model.Profile;
import com.example.wearegantt.repository.ProfileRepo;
import com.example.wearegantt.repository.TicketRepo;
import com.example.wearegantt.repository.UserRepo;

public class ObjectManager {

//  =============  Repositories ===============

    public TicketRepo   ticketRepo  = new TicketRepo();
    public UserRepo     userRepo    = new UserRepo();
    public ProfileRepo  profileRepo = new ProfileRepo();


}
