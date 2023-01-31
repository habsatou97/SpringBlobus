package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    @Column(
            nullable = false,
            unique = true)

    private String encryptedPinCode;
    private WalletType walletType;
    @Column(
            nullable = false,
            unique = true)

    private String phoneNumber;
    boolean is_active;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "retailer_id")
    private User retailer;

    public User getRetailer() {
        return retailer;
    }

    public void setRetailer(User retailer) {
        this.retailer = retailer;
    }
}
