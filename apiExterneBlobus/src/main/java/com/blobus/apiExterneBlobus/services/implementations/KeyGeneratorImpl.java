package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.DecryptDto;
import com.blobus.apiExterneBlobus.dto.KeyDto;
import com.blobus.apiExterneBlobus.services.interfaces.KeyGeneratorService;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    public KeyDto getPublicKeyFromFile() throws IOException {
        KeyDto keyDto=new KeyDto();
        String path = "RSA/pubkey";
        keyDto.setKeySize(1024);
        keyDto.setKeyType("RSA");
        keyDto.setKey(readFromFile(path));
        return keyDto;
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
    public  String encrypt(DecryptDto decryptDto) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {

            PublicKey key=(PublicKey) getPublicKey();
            System.out.println(key);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            System.out.println(" Encrypted:"+cipher.doFinal(decryptDto.getEncryptedPinCode().getBytes()));
            byte[] crypt= cipher.doFinal(decryptDto.getEncryptedPinCode().getBytes());
            return new String(Base64.getEncoder().encodeToString(crypt));

    }

    @Override
    public String decrypt(DecryptDto decryptDto) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PrivateKey privateKey=getPrivateKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(decryptDto.getEncryptedPinCode())));
    }
/*
    public  String decrypt(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey());
    }*/


}
