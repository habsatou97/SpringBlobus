package com.blobus.apiExterneBlobus;

//import com.blobus.apiExterneBlobus.repositories.AccountRepository;
//import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.blobus.apiExterneBlobus.models.KeyGenerator.writeToFileString;


@SpringBootApplication
@Configuration
public class ApiExterneBlobusApplication
{
	public static void main(String[] args) {
		SpringApplication.run(ApiExterneBlobusApplication.class, args);

	}

	@Bean
	public void generateKeyRsa() throws NoSuchAlgorithmException, IOException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair pair = keyGen.generateKeyPair();
		String pub_key = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
		String priv_key = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
		writeToFileString(pub_key, priv_key);
	}


}
