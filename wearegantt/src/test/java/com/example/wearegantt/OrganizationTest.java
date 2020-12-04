package com.example.wearegantt;

import com.example.wearegantt.model.Organization;
import com.example.wearegantt.model.Project;
import com.example.wearegantt.repository.OrganizationRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrganizationTest {

    OrganizationRepo orgRep = new OrganizationRepo();

    @Test
    public void getOneOrg(){
//        Organization orgObj = orgRep.getOneOrg(1);

//        System.out.println(orgObj);
    }

    @Test
    public void insertOrg(){
        Organization orgObj = orgRep.insertOrg("db", "asd", 123);

        System.out.println(orgObj);
    }

    @Test
    public void getAllOrgs(){
        List<Organization> listOrgs = orgRep.getAllOrgs();

        System.out.println(listOrgs);
    }

    @Test
    public void updateOrg(){
        orgRep.updateOrg(2,"db2", "dsa", 321);

    }

}
