package com.blobus.apiexterneblobus.repositories;

import com.blobus.apiexterneblobus.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
