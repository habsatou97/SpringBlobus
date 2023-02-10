package com.blobus.apiExterneBlobus.services.interfaces;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

@Component
public interface KeyGeneratorService {

    public String getPublicKeyFromFile() throws IOException;
    public  PublicKey getPublicKey() throws IOException;
    public  PrivateKey getPrivateKey() throws IOException;

   // PrivateKey getPrivateKey(String privateKey) throws IOException;

    public String  getPrivateKeyFromFile() throws  IOException;
    public String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;
    public String decrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    //public  String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException;
}
