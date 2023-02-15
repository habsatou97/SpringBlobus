package com.blobus.apiExterneBlobus.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bulks")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Bulk{
    @Id
    @GeneratedValue
    private Long bulkId;

    @ManyToOne
    @JoinColumn(name = "retailer_id" ,nullable = true)
    private User retailer;

    @OneToMany(mappedBy = "bulk")
    @ToString.Exclude
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransactions(Transaction transaction){ transactions.add(transaction); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bulk bulk = (Bulk) o;
        return bulkId != null && Objects.equals(bulkId, bulk.bulkId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
