package com.blobus.apiexterneblobus.models;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

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
