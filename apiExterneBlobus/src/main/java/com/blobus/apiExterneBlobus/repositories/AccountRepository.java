package com.blobus.apiExterneBlobus.repositories;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> getAccountByPhoneNumber(String  phoneNumber);
    Optional<Account> findByEncryptedPinCodeAndPhoneNumberAndRetailer(String encryptedPinCode,String  phoneNumber, User retailer);
    Optional<Account> findByPhoneNumberAndWalletType(String phoneNumber, WalletType walletType);

}
