package com.blobus.apiexterneblobus.services.interfaces;

import com.blobus.apiexterneblobus.dto.DecryptDto;
import com.blobus.apiexterneblobus.dto.KeyDto;
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

    public KeyDto getPublicKeyFromFile() throws IOException;
    public  PublicKey getPublicKey() throws IOException;
    public  PrivateKey getPrivateKey() throws IOException;

   // PrivateKey getPrivateKey(String privateKey) throws IOException;

    public String  getPrivateKeyFromFile() throws  IOException;
    public String encrypt(DecryptDto decryptDto) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;
    public String decrypt(DecryptDto decryptDto) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    //public  String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException;
}
