package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.UserDto;
import com.blobus.apiexterneblobus.dto.UserWithNineaDto;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import com.blobus.apiexterneblobus.services.implementations.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/ewallet/v1/admin/users/")
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
    @Operation(summary = "This operation allows to get all the users of the API ")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestHeader(required = true,name = "Authorization")String token){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    /**
     * Ce endPoint permet de visualiser un utilisateur de l'api via son identifiant
     * @param id
     * @return
     */
    @Operation(summary = "This operation allows to get a user by his ID")
    @GetMapping("{id}")
    public ResponseEntity<Optional<UserDto>> getOne(@Parameter(description = "An ID is required to process this operation") @PathVariable("id") Long id,
                                                    @RequestHeader(required = true,name = "Authorization")String token){
        return ResponseEntity.ok(userService.getOneUser(id));
    }

    /**
     * Ce endPoint permet de visualiser l'ensemble des retailers de l'api
     *
     * @return ResponseEntity<List<UserDto>>
     */
    @Operation(summary = "This operation allows to get all users retailers")
    @GetMapping("retailers")
    public ResponseEntity<List<UserDto>> getAllRetailer(@RequestHeader(required = true,name = "Authorization")String token){
        return ResponseEntity.ok(userService.getAllRetailer());
    }


    /**
     * ce endPoint permet à l'administrateur d'ajouter  un utilisateur de l'api
     * @param user
     * @return
     */
    @Operation(summary = "This operation allows to get all users retailers")
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody(required = true) UserWithNineaDto user,
                                           @RequestHeader(required = true,name = "Authorization")String token){
        return ResponseEntity.ok(userService.addSingleUser(user));
    }

    /**
     * Ce endPoint permet à l'asministrateur de modifer un utilisateur de l'api
     * @param user
     * @param id
     * @return ResponseEntity<UserDto>
     */
    @Operation(summary = "This operation allows to update an user by his ID")
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserWithNineaDto user,
            @Parameter(description = "The ID of the user to update is required")@PathVariable("id") Long id,@RequestHeader(required = true,name = "Authorization")String token){

        return ResponseEntity.ok(userService.updateSingleUser(user,id));
    }

    /**
     * Ce endPoint permet à l'administrateur de supprimer un utilisateur de l'api
     * @param id
     * @return
     */
    @Operation(summary = "This operation allows to delete an user by his ID")
    @DeleteMapping("{id}")
    public  ResponseEntity<Map<String,Boolean>> deleteUser(@Parameter(description = "The ID of the user to delete is required")@PathVariable("id") Long id,@RequestHeader(required = true,name = "Authorization")String token){

        Map<String,Boolean> response = new HashMap<>();
        userService.deleteUser(id);
        response.put("deleted",true);

        return ResponseEntity.ok(response);
    }
}
