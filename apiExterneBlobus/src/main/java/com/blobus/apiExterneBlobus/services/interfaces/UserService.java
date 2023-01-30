package com.blobus.apiExterneBlobus.services.interfaces;

import com.blobus.apiExterneBlobus.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User addSingleUser(User user);
    public User updateSingleUser(User user,Long id);
    public List<User> getAllUsers();
    public Optional<User> getOneUser(Long id);
    public void deleteUser(Long id);
    public User getUserProfileByMsisdn(String phoneNumber);
}
