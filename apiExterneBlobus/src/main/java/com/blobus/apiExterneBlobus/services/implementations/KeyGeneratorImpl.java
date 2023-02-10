package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.services.interfaces.KeyGeneratorService;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

import static com.blobus.apiExterneBlobus.models.KeyGenerator.readFromFile;

@Service

public class KeyGeneratorImpl implements KeyGeneratorService {


    @Override
    public  String getPublicKeyFromFile() throws IOException {
        String path = "RSA/pubkey";
         return readFromFile(path);
    }

    @Override
    public PublicKey getPublicKey() throws IOException {
        //PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(readFromFile("RSA/pubkey").getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
            //return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PrivateKey getPrivateKey() throws IOException {
        PrivateKey cle = null;
        //privateKey=readFromFile("RSA/pubkey");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(readFromFile("RSA/privkey").getBytes()));
        KeyFactory keyFactory=null;
        try {
             keyFactory = KeyFactory.getInstance("RSA");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            cle = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return cle;

    }

    @Override
    public String getPrivateKeyFromFile() throws IOException {
        String path = "RSA/privkey.txt";
        return readFromFile(path);
    }

    @Override
    public  String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {

            PublicKey key=(PublicKey) getPublicKey();
            System.out.println(key);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            System.out.println(" Encrypted:"+cipher.doFinal(data.getBytes()));
            byte[] crypt= cipher.doFinal(data.getBytes());
            return new String(Base64.getEncoder().encodeToString(crypt));

    }

    @Override
    public String decrypt(String data) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PrivateKey privateKey=getPrivateKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
    }
/*
    public  String decrypt(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey());
    }*/


}
