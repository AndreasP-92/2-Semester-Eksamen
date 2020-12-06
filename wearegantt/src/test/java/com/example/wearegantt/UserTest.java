package com.example.wearegantt;

import com.example.wearegantt.repository.UserRepo;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void updatePassword(){
        UserRepo.updateCredentials(29,"test","321");
    }
}
