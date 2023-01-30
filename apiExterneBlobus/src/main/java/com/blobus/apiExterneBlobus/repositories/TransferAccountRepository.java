package com.blobus.apiExterneBlobus.repositories;

import com.blobus.apiExterneBlobus.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferAccountRepository extends JpaRepository<Account,Long> {
}
