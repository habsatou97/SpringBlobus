package com.blobus.apiExterneBlobus.repositories;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> getAccountByPhoneNumber(String  phoneNumber);
    Optional<Account> findByEncryptedPinCodeAndPhoneNumberAndRetailer(String encryptedPinCode,String  phoneNumber, User retailer);

}
