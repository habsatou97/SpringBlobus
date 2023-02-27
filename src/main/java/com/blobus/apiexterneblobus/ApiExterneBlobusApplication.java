package com.blobus.apiexterneblobus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.blobus.apiexterneblobus.models.KeyGenerator.writeToFileString;


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


	@Bean
	public CorsFilter corsFilter() {
	 	CorsConfiguration corsConfiguration = new CorsConfiguration();
	 	corsConfiguration.setAllowCredentials(true);
	 	corsConfiguration.setAllowedOrigins(List.of("https://blobus-backend-wepapi.azurewebsites.net","https://localhost:44311","http://localhost:4200","http://192.168.1.146:3000","http://192.168.197.233:3000"));
	 	corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
	 			"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
	 			"Access-Control-Request-Method", "Access-Control-Request-Headers"));
	 	corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
	 			"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
	 	corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	 	UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
	 	urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
	 	return new CorsFilter(urlBasedCorsConfigurationSource);
	 }


}
