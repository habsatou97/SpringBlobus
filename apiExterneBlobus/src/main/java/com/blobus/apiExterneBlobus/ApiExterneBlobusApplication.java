package com.blobus.apiExterneBlobus;

import com.blobus.apiExterneBlobus.models.TransferAccount;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.TransferAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiExterneBlobusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiExterneBlobusApplication.class, args);
	}

}
