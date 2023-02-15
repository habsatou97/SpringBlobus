package com.blobus.apiExterneBlobus.controllers;


import com.blobus.apiExterneBlobus.dto.DecryptDto;
import com.blobus.apiExterneBlobus.dto.KeyDto;
import com.blobus.apiExterneBlobus.services.interfaces.KeyGeneratorService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("ewallet/api/v1/accounts/publicKeys/")
public class KeyController {
    @Autowired
    private final KeyGeneratorService keyGenerator;

    @GetMapping
    public ResponseEntity<KeyDto> get() throws IOException {
       return ResponseEntity.ok().body(keyGenerator.getPublicKeyFromFile());
    }


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
        return keyGenerator.decrypt(decryptDto);
    }
}

