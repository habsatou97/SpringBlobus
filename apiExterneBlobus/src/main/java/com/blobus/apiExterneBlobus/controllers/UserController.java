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

/**
 * @author bibiche-97
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ewallet/v1/users/")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;
    @Resource
    private final UserRepository userRepository;

    /**
     *
     * cette methode l'ensemble des utilisateur de l'api.
     * @return
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * @param phoneNumber
     * @return
     * cette methode affiche le profile d'un utilisateur via son msisdn autrement dit via le numero de telephone de son compte tranfert
     */
    @GetMapping("find/{phoneNumber}")
    public  ResponseEntity<RequestBodyUserProfileDto> getUserProfileByMsisdn(@PathVariable("phoneNumber") String phoneNumber){

        return ResponseEntity.ok(userService.getUserProfileByMsisdn(phoneNumber));
    }

    /**
     * Cette methoden permet d'afficher un seul itilisateur via son idntifiant
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Optional<User>> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getOneUser(id));
    }

    /**
     *
     * @return
     * cette methode retourne la listes de tous les retailers de l'api
     */
    @GetMapping("retailers")
    public ResponseEntity<List<User>> getAllRetailer(){
        return ResponseEntity.ok(userService.getAllRetailer());
    }


    /**
     * Cette methode permet d'ajouter un utilisateur
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addSingleUser(user));
    }

    /**
     * Cette methode permet de modifer un utilisateur
     * @param user
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id){
        return ResponseEntity.ok(userService.updateSingleUser(user,id));
    }

    /**
     * cette methode permet de supprimer un utilisateur
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
