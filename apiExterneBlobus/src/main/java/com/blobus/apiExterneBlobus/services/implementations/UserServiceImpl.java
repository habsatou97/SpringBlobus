package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.AmountDto;
import com.blobus.apiExterneBlobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiExterneBlobus.dto.UserDto;
import com.blobus.apiExterneBlobus.dto.UserWithNineaDto;
import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    private final AccountRepository transferAccountRepository;

    @Override
    public UserDto addSingleUser(UserWithNineaDto user) {
        UserDto userDto=new UserDto();

        if(
                user.getFirstName()!=null &&
                user.getLastName()!=null &&
                user.getEmail() != null &&
                user.getPhoneNumber() !=null && user.getPhoneNumber().length() == 9)
        {
            Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
            Optional<User> userOptional1 = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
            if(userOptional.isPresent() || userOptional1.isPresent()){
                throw new IllegalStateException("Oups! cette email "+ user.getEmail()+" existe deja");
            }
            if( user.getNinea()!= null)
                user.setRoles(Collections.singletonList(Role.RETAILER));
           User user1= User.builder()
                          .firstName(user.getFirstName())
                          .lastName(user.getLastName())
                          .email(user.getEmail())
                          .phoneNumber(user.getPhoneNumber())
                          .roles(user.getRoles())
                          .ninea(user.getNinea())
                                  .build();

            userRepository.save(user1);
            userDto.setEmail(user.getEmail());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            return userDto;
        }
           throw new IllegalStateException("Veuillez renseignez tous les champs correctement");
    }

    @Override
    @Transactional
    public UserDto updateSingleUser(UserWithNineaDto user, Long id) {
        UserDto userDto= new UserDto();
      User user1 = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("updated failled ,user_id not found"));
      if (user.getFirstName()!=null && user.getFirstName().length()>0
            && !Objects.equals(user1.getFirstName(),user.getFirstName()))
      {
          user1.setFirstName(user.getFirstName());
      }

        if (user.getLastName()!=null && user.getLastName().length()>0
                && !Objects.equals(user1.getLastName(),user.getLastName()))
        {
            user1.setLastName(user.getLastName());
        }
        if (user.getEmail() !=null && user.getEmail().length()>0
                && !Objects.equals(user1.getEmail(),user.getEmail())){
            Optional<User> userOptional= userRepository.findUserByEmail(user.getEmail());
            if(userOptional.isPresent()){
                throw new IllegalStateException("Cette email existe deja");
            }
            user1.setEmail(user.getEmail());
        }
        if (user.getPhoneNumber() !=null && user.getPhoneNumber().length()>0
                && !Objects.equals(user1.getPhoneNumber(),user.getPhoneNumber())){
            Optional<User> userOptional1= userRepository.findUserByPhoneNumber(user.getPhoneNumber());
            if(userOptional1.isPresent()){
                throw new IllegalStateException("Ce numero de telephone existe deja");
            }
            user1.setPhoneNumber(user.getPhoneNumber());
        }
        user1.setRoles(user.getRoles());
        user1.setNinea(user.getNinea());
        userRepository.saveAndFlush(user1);
        userDto.setEmail(user1.getEmail());
        userDto.setLastName(user1.getLastName());
        userDto.setFirstName(user1.getFirstName());
        userDto.setPhoneNumber(user1.getPhoneNumber());
        return  userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(
                user -> {
                    UserDto dto = new UserDto();
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    dto.setFirstName(user.getFirstName());
                    dto.setPhoneNumber(user.getPhoneNumber());
                    return dto;
                }).toList();
    }

    @Override
    public Optional<UserDto> getOneUser(Long id) {
        return userRepository.findById(id).stream().map(
                user -> {
            UserDto dto = new UserDto();
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            dto.setFirstName(user.getFirstName());
            dto.setPhoneNumber(user.getPhoneNumber());
            return dto;
        }).findAny();
    }

    @Override
    public void deleteUser(Long id) {
       userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("deleted failled ,user_id "+id+" not found"));
       userRepository.deleteById(id);
    }

    @Override
    public RequestBodyUserProfileDto getUserProfileByMsisdn(String phoneNumber) {

        RequestBodyUserProfileDto userProfileDto = new RequestBodyUserProfileDto();
        AmountDto amountDto= new AmountDto();
        Account account = transferAccountRepository.getAccountByPhoneNumber(phoneNumber).orElseThrow(() ->
                new ResourceNotFoundException("msisdn invalid"));
        amountDto.setCurrency(TransactionCurrency.XOF);
        amountDto.setValue(account.getBalance());
        userProfileDto.setMsisdn(account.getPhoneNumber());
        userProfileDto.setBalance(amountDto);
        userProfileDto.setSuspended(account.is_active());
        if(account.getCustomer()!=null){
            userProfileDto.setType(String.valueOf(CustomerType.CUSTOMER));
            userProfileDto.setLastName(account.getCustomer().getLastName());
            userProfileDto.setFirstName(account.getCustomer().getFirstName());
        }
        else if(account.getRetailer()!=null){
            userProfileDto.setType(String.valueOf(CustomerType.RETAILER));
            userProfileDto.setLastName(account.getRetailer().getLastName());
            userProfileDto.setFirstName(account.getRetailer().getFirstName());
            userProfileDto.setUserId(account.getRetailer().getUserId());
        }
        return userProfileDto;
    }

    @Override
    public List<UserDto> getAllRetailer() {
        List<User> users = new ArrayList<>();
        List<User> users1 = userRepository.findAll();
        for (User user: users1){
            if (user.getRoles().contains(Role.RETAILER)){
                users.add(user);
            }
        }
        return users.stream().map(user -> {
            UserDto dto = new UserDto();
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            dto.setFirstName(user.getFirstName());
            dto.setPhoneNumber(user.getPhoneNumber());
            return dto;
        }).toList();
    }
}
