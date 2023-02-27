package com.blobus.apiexterneblobus.repositories;

import com.blobus.apiexterneblobus.models.Account;
import com.blobus.apiexterneblobus.models.enums.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferAccountRepository extends JpaRepository<Account,Long> {
    // pour recuperer un compte via le numero de telephone et le type de compte

    Optional<Account> findByPhoneNumberAndWalletType(String phoneNumbber, WalletType walletType);
}
