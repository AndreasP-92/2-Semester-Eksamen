package com.example.wearegantt.services;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Profile;
import com.example.wearegantt.repository.*;

public class ObjectManager {

//  =============  Repositories ===============

    public TicketRepo       ticketRepo          = new TicketRepo();
    public UserRepo         userRepo            = new UserRepo();
    public ProfileRepo      profileRepo         = new ProfileRepo();
    public OrganizationRepo organizationRepo    = new OrganizationRepo();
    public JobTitleRepo     jobTitleRepo        = new JobTitleRepo();
    public ProjectRepo      projectRepo         = new ProjectRepo();
    public NewsfeedRepo     newsRepo            = new NewsfeedRepo();

}
