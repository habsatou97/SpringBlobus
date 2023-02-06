package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest1 {

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
        // initialise un utilisateur
            String email ="barry.dame@avimtoo.com";
        User user = new User();
        user.setNinea("vimto1245");
        user.setPhoneNumber("782654426");
        user.setUserId("fzidgjd");
        user.setUserSecret("ofy78");
        user.setLastName("Barry");
        user.setFirstName("Dame");
        user.setEmail(email);
        user.setRoles(Collections.singletonList(Role.RETAILER));

        // persister l'utlisateur dans la base de données
        repository.save(user);

        // je recuper l'utilisateur
       User user1= repository.findById(user.getId()).orElseThrow();
       // je modifie l'utlisateur
       user1.setLastName("El-seydi");
       user1.setFirstName("Ba");
       user1.setEmail("barry.pape-dame@avimtoo.com");
       //
       repository.save(user1);
        Assertions.assertThat(repository.findById(user1.getId())).isNotEmpty();
    }


    @Test
    @AutoConfigureTestDatabase
    void deleteUser() {
        // Vider la base de donnee
        repository.deleteAll();
        //initialiser un utilisateur
        String email = "aby.barry@avimtoo.com";
        User user = new User();
             user.setFirstName("El Seydi");
             user.setLastName( "Ba");
             user.setEmail(email);
             user.setRoles(Collections.singletonList(Role.RETAILER));
             user.setNinea("vimto1245");
             user.setPhoneNumber("71954362");
             user.setUserId("fzivbedegjd");
             user.setUserSecret("fohgfyf78");
 // persister l'utilisateur dans la base de données
         repository.save(user);
       // supprimer l'utilisateur
        repository.deleteById(user.getId());
        // verifier si la suppression est effectives
        Assertions.assertThat(repository.count()).isZero();

    }

      private User getUser(){
        return repository.findById(Long.valueOf(1)).orElseThrow();
    }


    private User createUser(){
        String email = "bibiche.diouf@avimtoo.com";
        User user = new User(
                6L,
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