package com.blobus.apiExterneBlobus;

import com.blobus.apiExterneBlobus.models.TransferAccount;
import com.blobus.apiExterneBlobus.models.enumerations.WalletType;
import com.blobus.apiExterneBlobus.repositories.TransferAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiExterneBlobusApplication implements CommandLineRunner {
	@Autowired
    public TransferAccountRepository transferAccountRepository;

	public ApiExterneBlobusApplication(TransferAccountRepository transferAccountRepository) {
		this.transferAccountRepository = transferAccountRepository;
	}
	public void run(String... args){
		TransferAccount account1=new TransferAccount(1l,12345.00,1234, WalletType.SALAIRE,Boolean.FALSE);
		TransferAccount account2=new TransferAccount(2l,12345.00,0000, WalletType.SALAIRE,Boolean.FALSE);
		TransferAccount account3=new TransferAccount(3l,12345.00,0001, WalletType.SALAIRE,Boolean.FALSE);
		TransferAccount account4=new TransferAccount(4l,12345.00,1004, WalletType.SALAIRE,Boolean.FALSE);

		transferAccountRepository.save(account1);
		transferAccountRepository.save(account2);
		transferAccountRepository.save(account3);
		transferAccountRepository.save(account4);


	}

	public static void main(String[] args) {
		SpringApplication.run(ApiExterneBlobusApplication.class, args);
	}

}
