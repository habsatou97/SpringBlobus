package com.blobus.apiexterneblobus.services.interfaces;

import com.blobus.apiexterneblobus.dto.UserDto;
import com.blobus.apiexterneblobus.dto.UserWithNineaDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public UserDto addSingleUser(UserWithNineaDto user);
    public UserDto updateSingleUser(UserWithNineaDto user,Long id);
    public List<UserDto> getAllUsers();
    public Optional<UserDto> getOneUser(Long id);
    public void deleteUser(Long id);
   public List<UserDto> getAllRetailer();
}
