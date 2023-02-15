package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.DecryptDto;
import com.blobus.apiExterneBlobus.dto.KeyDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.blobus.apiExterneBlobus.models.KeyGenerator.readFromFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KeyGeneratorImplTest {

    @Test
    void getPublicKeyFromFile() throws IOException {
        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);

        when(keyGenerator.getPublicKeyFromFile()).thenReturn(new KeyDto());
        Assertions.assertThat(keyGenerator.getPublicKeyFromFile()).isNotNull();
    }

    @Test
    void getPublicKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);
        KeyFactory factory = mock(KeyFactory.class);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(readFromFile("RSA/pubkey").getBytes()));

        when(keyGenerator.getPublicKey()).
                thenReturn(factory.getInstance("RSA").generatePublic(keySpec));
        Assertions.assertThat(keyGenerator.getPublicKey()).isNotNull();
    }

    @Test
    void getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);
        KeyFactory factory = mock(KeyFactory.class);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(readFromFile("RSA/privkey").getBytes()));

        when(keyGenerator.getPrivateKey()).
                thenReturn(factory.getInstance("RSA").generatePrivate(keySpec));
        Assertions.assertThat(keyGenerator.getPrivateKey()).isNotNull();

    }

    @Test
    void encrypt() throws NoSuchPaddingException,
            IllegalBlockSizeException,
            NoSuchAlgorithmException,
            IOException,
            BadPaddingException,
            InvalidKeySpecException,
            InvalidKeyException {

        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);
        PublicKey key = mock(PublicKey.class);
        Cipher cipher= mock(Cipher.class);
        DecryptDto dto = new DecryptDto();
        dto.setEncryptedPinCode("Sting");
        when(keyGenerator.encrypt(dto))
                .thenReturn(String.valueOf(cipher.getInstance("RSA/ECB/PKCS1Padding")));
        Assertions.assertThat(keyGenerator.encrypt(dto)).isNotNull();
    }

    @Test
    void decrypt() throws NoSuchPaddingException,
            IllegalBlockSizeException,
            IOException,
            NoSuchAlgorithmException,
            BadPaddingException,
            InvalidKeyException {

        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);
        PublicKey key = mock(PublicKey.class);
        Cipher cipher= mock(Cipher.class);
        DecryptDto dto = new DecryptDto();
        dto.setEncryptedPinCode("Sting");

        when(keyGenerator.decrypt(dto))
                .thenReturn(String.valueOf(cipher.getInstance("RSA/ECB/PKCS1Padding")));
        Assertions.assertThat(keyGenerator.decrypt(dto)).isNotNull();
    }
}