package com.blobus.apiexterneblobus.controllers;


import com.blobus.apiexterneblobus.dto.DecryptDto;
import com.blobus.apiexterneblobus.dto.KeyDto;
import com.blobus.apiexterneblobus.services.interfaces.KeyGeneratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ewallet/v1/all/accounts/publicKeys/")
public class KeyController {
    @Autowired
    private final KeyGeneratorService keyGenerator;

    @GetMapping
    @Operation(summary = "This operation allow to get the public Key that is use for the pin code encryption")
    ///@ApiResponse(responseCode = "201",description = "Key returned sucessfully with its size and type")
    //@ApiResponse(responseCode = "400",description = "Invalid request")
    //@Parameter(description = "This request need no parameters")

    public ResponseEntity<KeyDto> get() throws IOException {
        return ResponseEntity.ok().body(keyGenerator.getPublicKeyFromFile());
    }

/*
    @PostMapping("crypt")
    public ResponseEntity<DecryptDto> encrypt(@RequestBody DecryptDto decryptDto)
                                     throws NoSuchAlgorithmException,
                                            NoSuchPaddingException,
                                            IllegalBlockSizeException,
                                            NoSuchAlgorithmException,
                                            IOException,
                                            BadPaddingException,
                                            InvalidKeySpecException,
                                            InvalidKeyException {
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");

        DecryptDto decryptDto1=new DecryptDto();
        decryptDto1.setEncryptedPinCode(keyGenerator.encrypt(decryptDto));
        return ResponseEntity.ok().body(decryptDto1);
    }
    @PostMapping("decrypt")
    public String decrypt(@RequestBody  DecryptDto decryptDto)
            throws NoSuchPaddingException,
            IllegalBlockSizeException,
            NoSuchAlgorithmException,
            IOException,
            BadPaddingException,
            InvalidKeyException {
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");

        return keyGenerator.decrypt(decryptDto);
    }*/
}

