package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void setUp() throws Exception  {
       userService = new UserServiceImpl(userRepository,accountRepository);
    }

    @Test
    void getAllUsers() {
        // when
        userService.getAllUsers();

        // verify
        verify(userRepository).findAll();
    }
    @Test
    void getOneUser() {

        // when
        userService.getOneUser(Long.valueOf(1));
        // verify

        verify(userRepository).findById(Long.valueOf(1));
    }

    @Test
    void getAllRetailer() {
        //when
        userService.getAllRetailer();
        //verify
        verify(userRepository).findAll();
    }

    @Test
    @AutoConfigureTestDatabase
    void addSingleUser()  throws Exception{
        // given
        User user=createUser();
        //when
        userService.addSingleUser(user);
        // then
      /*  assertThatThrownBy(()->
                userService.addSingleUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Oups! cette email "+ user.getEmail()+" existe deja");
*/
        ArgumentCaptor<User> userArgumentCaptor=
                ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser= userArgumentCaptor.getValue();
        Assertions.assertThat(capturedUser).isEqualTo(user);
/*
        Assertions.assertThat(userRepository.findById(user.getId()))
                    .isNotEmpty();
*/
    }

   @Test
    void updateSingleUser() {
            String email ="barry.pape-dame@avimtoo.com";
        User user =repository.findById(Long.valueOf(102)).orElseThrow();
        user.setLastName("Barry");
        user.setFirstName("Dame");
        user.setEmail(email);
        user.setRoles(Collections.singletonList(Role.RETAILER));
        repository.save(user);
        Assertions.assertThat(repository.findById(user.getId())).isNotEmpty();
    }


   /* @Test
    @Disabled
    void deleteUser() {
        Mockito.when(userRepository.findById(getUser().getId())).thenReturn(Optional.of(getUser()));
    }

    @Test
    void getUserProfileByMsisdn() {
        String phoneNumber = "782654426";
        userService.getUserProfileByMsisdn(phoneNumber);
      verify(accountRepository).getAccountByPhoneNumber(phoneNumber);
        //given

    }*/

    private User getUser(){
        return repository.findById(Long.valueOf(1)).orElseThrow();
    }


    private User createUser(){
        String email = "bibiche.diouf@avimtoo.com";
        User user = new User(
                6l,
                "El Seydi",
                "Ba",
                email ,
                Role.RETAILER,
                "vimto1245",
                "718954362",
                "fzivbedfegjd",
                "fohfgfyf78");
        return user;
    }

}