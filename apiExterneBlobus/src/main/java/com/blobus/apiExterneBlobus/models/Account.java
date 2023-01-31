package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transferAccounts")
@Data
@RequiredArgsConstructor
@AllArgsConstructor


public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private double balance;
    @Column(nullable = false)
    private String encryptedPinCode;
    private WalletType walletType;
    @Column(nullable = false,unique = true)
    private String phoneNumber;
    boolean is_active;
    @JsonIgnore
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id" ,referencedColumnName = "id",nullable = true)
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "retailer_id" ,referencedColumnName = "id",nullable = true)
    private User retailer;

    @OneToMany(mappedBy = "customerTransferAccount")
    List<Transaction> customerTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "retailerTransferAccount")
    List<Transaction> retailerTransactions = new ArrayList<>();

}
