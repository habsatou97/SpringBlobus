package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @AutoConfigureTestDatabase
    void addSingleUser() {
        final User user=createUser();
        try {
            userRepository.save(user);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            Assertions.assertThat(userRepository.findById(user.getId()))
                    .isNotEmpty();
        }
    }

   @Test
    void updateSingleUser() {

        User user =userRepository.findById(Long.valueOf(102)).orElseThrow();
        user.setLastName("Barry");
        user.setFirstName("Dame");
        user.setEmail("barry.pape-dame@avimtoo.com");
        user.setRoles(Collections.singletonList(Role.RETAILER));
        userRepository.save(user);
       OptionalAssert<User> userOptionalAssert = Assertions.assertThat(userRepository.findById(user.getId()));
       //Assertions.assertThat(userRepository.count()).isNotZero();
    }
/*
    @Test
    void getAllUsers() {
    }

    @Test
    void getOneUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserProfileByMsisdn() {
    }

    @Test
    void getAllRetailer() {
    }
*/
    private User createUser(){
        String email = "rtdgvtf.-gvfuhz@avimtoo.com";
        User user = new User(
                "Ablaye",
                "Faye",
                email ,
                Role.RETAILER,
                "789478566u",
                "754516329",
                        "fzivbeycjd",
                "fohfr52578@");
        return user;
    }

}