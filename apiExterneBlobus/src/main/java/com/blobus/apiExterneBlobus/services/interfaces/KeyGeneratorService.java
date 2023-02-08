package com.blobus.apiExterneBlobus.services.interfaces;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.PrivateKey;

@Component
public interface KeyGeneratorService {

    public String getPublicKeyFromFile() throws IOException;
    public PrivateKey getPrivateKey();


}
