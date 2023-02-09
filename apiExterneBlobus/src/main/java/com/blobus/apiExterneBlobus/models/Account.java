package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transferAccounts")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private double balance;
    @Column(nullable = false)
    private String encryptedPinCode;
    private WalletType walletType;
    @Column(nullable = false,length = 9)
    private String phoneNumber;
    boolean is_active;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id" ,referencedColumnName = "id",nullable = true)
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "retailer_id" ,referencedColumnName = "id",nullable = true)
    private User retailer;

    @OneToMany(mappedBy = "customerTransferAccount")
    List<Transaction> customerTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "retailerTransferAccount")
    List<Transaction> retailerTransactions = new ArrayList<>();

}
