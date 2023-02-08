package com.blobus.apiExterneBlobus.controllers;

//import com.blobus.apiExterneBlobus.models.Keye;
//import com.blobus.apiExterneBlobus.services.implementations.KeyServiceImpl;
import com.blobus.apiExterneBlobus.services.interfaces.KeyGeneratorService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("ewallet/api/v1/accounts/publicKeys/")
public class KeyController {
    @Autowired
    private final KeyGeneratorService keyGenerator;

    @GetMapping
    public String get() throws IOException {
       return keyGenerator.getPublicKeyFromFile();
    }


    /*@PostMapping("crypt")
    public String encrypt(@RequestBody String encryptedPinCode) throws NoSuchAlgorithmException {
        key.getKey();
        return keyService.encrypt(encryptedPinCode);
    }
    @PostMapping("decrypt")
    public String decrypt(@RequestBody String encryptedPinCode){
        return keyService.decrypt(encryptedPinCode);
    }*/
}

