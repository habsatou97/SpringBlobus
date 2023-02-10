package com.blobus.apiExterneBlobus.repositories;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> getAccountByPhoneNumber(String  phoneNumber);
    Optional<Account> findByPhoneNumberAndWalletType(String phoneNumber, WalletType walletType);
    void deleteAccountByPhoneNumber(String phoneNumber);
    Optional<Account> findById(Long id);
    Optional<Account> findByPhoneNumberAndWalletTypeAndEncryptedPinCode(String phoneNumber, WalletType WalletType, String encryptedPinCode);
}
