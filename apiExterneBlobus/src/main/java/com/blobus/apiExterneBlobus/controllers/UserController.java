package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ewallet/v1/users/")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;
    @Resource
    private final UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public Optional<User> getOne(@PathVariable("id") Long id){
        return userService.getOneUser(id);
    }

    /**
     * @author Bibi
     * Cette methode affiche la listes de tous les retailers
     */
    @GetMapping("retailers")
    public List<User> getAllRetailer(){
        return userService.getAllRetailer();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addSingleUser(user));
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id){
        return ResponseEntity.ok(userService.updateSingleUser(user,id));
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable("id") Long id){
        Map<String,Boolean> response = new HashMap<>();
        userService.deleteUser(id);
        response.put("deleted",true);
        return ResponseEntity.ok(response);
    }


    @ResponseStatus
    @GetMapping("find/{phoneNumber}")
    public  ResponseEntity<RequestBodyUserProfileDto> getUserProfileByMsisdn(@PathVariable("phoneNumber") String phoneNumber){

        return ResponseEntity.ok(userService.getUserProfileByMsisdn(phoneNumber));
  }


}
