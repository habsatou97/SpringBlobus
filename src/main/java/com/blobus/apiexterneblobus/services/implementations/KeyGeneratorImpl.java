package com.blobus.apiexterneblobus.services.implementations;

import com.blobus.apiexterneblobus.dto.DecryptDto;
import com.blobus.apiexterneblobus.dto.KeyDto;
import com.blobus.apiexterneblobus.services.interfaces.KeyGeneratorService;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

import static com.blobus.apiexterneblobus.models.KeyGenerator.readFromFile;

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
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                    Base64.getDecoder().decode(readFromFile("RSA/pubkey").getBytes()));
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
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(readFromFile("RSA/privkey").getBytes()));
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
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] crypt= cipher.doFinal(decryptDto.getEncryptedPinCode().getBytes());
        System.out.println("============================================");
        System.out.println(crypt.length);
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
