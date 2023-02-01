package com.blobus.apiExterneBlobus.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "bulks")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
