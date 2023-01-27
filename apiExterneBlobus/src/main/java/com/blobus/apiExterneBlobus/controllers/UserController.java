package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ewallet/v1/user")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;
    @Resource
    private final UserRepository userRepository;

    @GetMapping("/users")

    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getOne(@PathVariable("id") Long id){
        return userService.getOneUser(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addSingleUser(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id){
        return ResponseEntity.ok(userService.updateSingleUser(user,id));
    }

    @DeleteMapping("/users/{id}")
    public  void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }




}
