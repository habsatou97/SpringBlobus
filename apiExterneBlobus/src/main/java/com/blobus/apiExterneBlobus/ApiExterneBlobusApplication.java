package com.blobus.apiExterneBlobus;

import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.TransferAccount;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.TransferAccountRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;


@SpringBootApplication
public class ApiExterneBlobusApplication
{

	public static void main(String[] args) {
		SpringApplication.run(ApiExterneBlobusApplication.class, args);

	}


}

