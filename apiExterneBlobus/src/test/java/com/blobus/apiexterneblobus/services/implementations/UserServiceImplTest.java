package com.blobus.apiexterneblobus.services.implementations;

import com.blobus.apiexterneblobus.dto.UserDto;
import com.blobus.apiexterneblobus.dto.UserWithNineaDto;
import com.blobus.apiexterneblobus.models.User;
import com.blobus.apiexterneblobus.models.enums.Role;
import com.blobus.apiexterneblobus.repositories.AccountRepository;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;
    @Autowired
    private UserServiceImpl userService;

    @MockBean
    UserServiceImpl service;

    @InjectMocks
    UserServiceImpl uService;
    @Autowired
    private UserRepository repository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    MockMvc mockMvc;


    User user1= new  User(
            3l,
            "El Seydi",
            "Ba",
            "em-seydi.ba@avimtoo.com" ,
            Role.RETAILER,
            "vimto1245",
            "718954362",
            "fzivbedfegjd",
            "fohfgfyf78");

    User user2= new  User(
            2l,
            "Cheikh Yangkhouba",
            "Cisse",
            "cheikh-yangkhouba.cisse@avimtoo.com" ,
            Role.ADMIN,
            "vimto1245",
            "708954362",
            "fzivbeegjd",
            "fohfyf78");

    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void getAllUsers() {
        // when

        List<User> users1= userRepository.findAll();
        when(service.getAllUsers()).thenReturn(users1.stream().map(
                user ->{ UserDto dto = new UserDto();
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    dto.setFirstName(user.getFirstName());
                    dto.setPhoneNumber(user.getPhoneNumber());
                    return dto;
                }).toList());
        verify(userRepository).findAll();


    }
    @Test
    void getOneUser() {
        UserRepository userRepository = mock(UserRepository.class);
        UserServiceImpl service1 = mock(UserServiceImpl.class);
        Optional<User> users1= userRepository.findById(user1.getId());
      //when(userRepository.save(any(User.class))).then(returnsFirstArg());
        // when
        when(service.getOneUser(user1.getId())).thenReturn(
                users1.stream().map(
                        user ->{ UserDto dto = new UserDto();
                            dto.setLastName(user.getLastName());
                            dto.setEmail(user.getEmail());
                            dto.setFirstName(user.getFirstName());
                            dto.setPhoneNumber(user.getPhoneNumber());
                            return dto;
                        }).findAny());
        // verify
        Assertions.assertThat(service.getOneUser(user1.getId())).isNotNull();


    }

    @Test
    void getAllRetailer() {
        //when
        List<User> users1= userRepository.findAll();

       when(service.getAllRetailer()).thenReturn(users1.stream().map(
               user -> {
                   UserDto dto = new UserDto();
                   dto.setLastName(user.getLastName());
                   dto.setEmail(user.getEmail());
                   dto.setFirstName(user.getFirstName());
                   dto.setPhoneNumber(user.getPhoneNumber());
                   return dto;
               }).toList());
        //verify
        verify(userRepository).findAll();
    }

    @Test
    @AutoConfigureTestDatabase
    void addSingleUser()  throws Exception{
        // given
        UserWithNineaDto user=UserWithNineaDto.builder()
                .ninea("vbfggtrt")
                .firstName("Rokhya")
                .lastName("Ndiaye")
                .email("rokhya-ndiaye@avimtoo.com")
                .phoneNumber("768954362")
                .roles(Collections.singletonList(Role.RETAILER))
                .build();

        //when

       UserDto  userDto= uService.addSingleUser(user);
        when(service.addSingleUser(user)).thenReturn(userDto);
       assertNotNull(userDto.getFirstName());

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
        userRepository.save(user);

        // je recuper l'utilisateur
       UserWithNineaDto user1 =UserWithNineaDto.builder()
               .ninea("vbfggtrt")
               .firstName("Rokhya")
               .lastName("Ndiaye")
               .email("rokhya-ndiaye@avimtoo.com")
               .phoneNumber("768954362")
               .roles(Collections.singletonList(Role.RETAILER))
               .build();
       //
       when(service.updateSingleUser(user1,user.getId())).thenReturn(new UserDto());
        Assertions.assertThat(service.updateSingleUser(user1,user.getId())).isNotNull();
    }


    @Test
    @AutoConfigureTestDatabase
    void deleteUser() {

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

         userRepository.save(user);

        service.deleteUser(user.getId());

        Assertions.assertThat(userRepository.findById(user.getId()).isEmpty());

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