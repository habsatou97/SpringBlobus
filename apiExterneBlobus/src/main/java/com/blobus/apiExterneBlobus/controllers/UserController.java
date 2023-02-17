package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiExterneBlobus.dto.UserDto;
import com.blobus.apiExterneBlobus.dto.UserWithNineaDto;
import com.blobus.apiExterneBlobus.dto.WalletTypeDto;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author bibiche-97
 */

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RequestMapping("/api/ewallet/v1/users/")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;
    @Resource
    private final UserRepository userRepository;

    /**
     *
     * Ce endPoint permet de visualiser l'ensemble des utilisateurs de l'api.
     * @return ResponseEntity<List<UserDto>>
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    /**
     * Ce endPoint permet de visualiser un utilisateur de l'api via son identifiant
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Optional<UserDto>> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getOneUser(id));
    }

    /**
     * Ce endPoint permet de visualiser l'ensemble des retailers de l'api
     *
     * @return ResponseEntity<List<UserDto>>
     */
    @GetMapping("retailers")
    public ResponseEntity<List<UserDto>> getAllRetailer(){
        return ResponseEntity.ok(userService.getAllRetailer());
    }


    /**
     * ce endPoint permet à l'administrateur d'ajouter  un utilisateur de l'api
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserWithNineaDto user){
        return ResponseEntity.ok(userService.addSingleUser(user));
    }

    /**
     * Ce endPoint permet à l'asministrateur de modifer un utilisateur de l'api
     * @param user
     * @param id
     * @return ResponseEntity<UserDto>
     */
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserWithNineaDto user,
            @PathVariable("id") Long id){

        return ResponseEntity.ok(userService.updateSingleUser(user,id));
    }

    /**
     * Ce endPoint permet à l'administrateur de supprimer un utilisateur de l'api
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public  ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable("id") Long id){

        Map<String,Boolean> response = new HashMap<>();
        userService.deleteUser(id);
        response.put("deleted",true);

        return ResponseEntity.ok(response);
    }
}
