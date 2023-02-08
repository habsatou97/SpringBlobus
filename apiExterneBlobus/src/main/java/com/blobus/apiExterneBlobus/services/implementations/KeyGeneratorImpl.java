package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.services.interfaces.KeyGeneratorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.PrivateKey;

import static com.blobus.apiExterneBlobus.models.KeyGenerator.readFromFile;

@Service

public class KeyGeneratorImpl implements KeyGeneratorService {

    @Override
    public String getPublicKeyFromFile() throws IOException {
        String path = "RSA/pubkey.txt";
         return readFromFile(path);
    }
    @Override
    public PrivateKey getPrivateKey() {
        return null;
    }
}
