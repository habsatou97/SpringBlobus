package com.blobus.apiExterneBlobus.repositories;

import com.blobus.apiExterneBlobus.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
