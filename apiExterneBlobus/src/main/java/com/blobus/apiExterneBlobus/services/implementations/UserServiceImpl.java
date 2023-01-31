package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.repositories.TransferAccountRepository;
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
    private final TransferAccountRepository transferAccountRepository;

    @Override
    public User addSingleUser(User user) {
        if(
                user.getFirstName()!=null &&
                user.getLastName()!=null &&
                user.getEmail() != null &&
                user.getPhoneNumber() !=null)
        {
            Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
            Optional<User> userOptional1 = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
            if(userOptional.isPresent() || userOptional1.isPresent()){
                throw new IllegalStateException("Oups! cette utilisateur existe deja");
            }
            if( user.getNinea()!= null)
                user.setRoles(Collections.singletonList(Role.RETAILER));
            return userRepository.save(user);
        }
       return null ;
    }

    @Override
    @Transactional
    public User updateSingleUser(User user, Long id) {
      User user1 = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("updated failled ,user_id not founc"));
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
        return userRepository.saveAndFlush(user1);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getOneUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
       userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("deleted failled ,user_id not founc"));
       userRepository.deleteById(id);
    }

    @Override
    public RequestBodyUserProfileDto getUserProfileByMsisdn(String phoneNumber) {

        RequestBodyUserProfileDto userProfileDto = new RequestBodyUserProfileDto();
        Account account = transferAccountRepository.getAccountByPhoneNumber(phoneNumber).orElseThrow(() ->
                new ResourceNotFoundException("msisdn invalid"));
        userProfileDto.setMsisdn(account.getPhoneNumber());
        userProfileDto.setBalance(account.getBalance());
        userProfileDto.setSuspended(account.is_active());
        userProfileDto.setWalletType(account.getWalletType());
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
    public List<User> getAllRetailer() {
        List<User> users = new ArrayList<>();
        List<User> users1 = userRepository.findAll();
        for (User user: users1){
            if (user.getRoles().contains(Role.RETAILER)){
                users.add(user);
            }
        }
        return users;
    }
}
