package com.blobus.apiExterneBlobus.repositories;

import com.blobus.apiExterneBlobus.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferAccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> getAccountByPhoneNumber(String  phoneNumber);
}
