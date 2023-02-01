package com.blobus.apiExterneBlobus.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.apache.catalina.LifecycleState;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
@Data
public class Bulk {
    @Id
    @GeneratedValue
    private Long bulkId;

    @ManyToOne
    @JoinColumn(name = "retailer_id")
    private User retailer;

    @OneToMany(mappedBy = "bulk")
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransactions(Transaction transaction){ transactions.add(transaction); }
}
