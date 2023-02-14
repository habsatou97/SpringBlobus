package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.KeyDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

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
    @Disabled
    void getPublicKey() throws IOException, NoSuchAlgorithmException, InstantiationException, IllegalAccessException, InvalidKeySpecException {
        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);
        KeyFactory factory = mock(KeyFactory.class);
        when(keyGenerator.getPublicKey()).thenReturn(factory.generatePublic(KeySpec.class.newInstance()));
    }

    @Test
    void getPrivateKey() {
    }

    @Test
    void getPrivateKeyFromFile() {
    }

    @Test
    void encrypt() {
    }

    @Test
    void decrypt() {
    }
}