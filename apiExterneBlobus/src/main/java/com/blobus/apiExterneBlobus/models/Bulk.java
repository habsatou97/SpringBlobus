package com.blobus.apiExterneBlobus.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "bulks")
@Data
@NoArgsConstructor
public class Bulk{
    @Id
    @GeneratedValue
    private Long bulkId;

    @ManyToOne
    @JoinColumn(name = "retailer_id" ,nullable = true)
    private User retailer;

    @OneToMany(mappedBy = "bulk")
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransactions(Transaction transaction){ transactions.add(transaction); }
}
