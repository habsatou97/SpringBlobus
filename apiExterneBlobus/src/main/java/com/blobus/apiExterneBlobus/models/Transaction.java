package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
import com.blobus.apiExterneBlobus.models.enums.TransactionStatus;
import com.blobus.apiExterneBlobus.models.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reference;
    private LocalDate createdDate;
    private Boolean receiveNotificatiion;
    private LocalDate requestDate;
    private TransactionStatus status;
    private TransactionType type;
    private Double amount;
    private TransactionCurrency currency;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "retailer_transfert_account_id")
    private Account retailerTransferAccount;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_transfert_account_id")
    private Account customerTransferAccount;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "bulk_id",nullable = true)
    private Bulk bulk;
}
