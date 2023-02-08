package com.blobus.apiExterneBlobus.services.interfaces;

import com.blobus.apiExterneBlobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiExterneBlobus.dto.UserDto;
import com.blobus.apiExterneBlobus.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public UserDto addSingleUser(User user);
    public UserDto updateSingleUser(User user,Long id);
    public List<UserDto> getAllUsers();
    public Optional<UserDto> getOneUser(Long id);
    public void deleteUser(Long id);
    public RequestBodyUserProfileDto getUserProfileByMsisdn(String phoneNumber);

   public List<UserDto> getAllRetailer();
}
