package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
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
    @Column(nullable = false)
    private String encryptedPinCode;
    private WalletType walletType;
    @Column(
            nullable = false,
            unique = true)

    private String phoneNumber;
    @Column(
            nullable = true)
    boolean is_active;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Nullable
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "retailer_id")
    @Nullable
    private User retailer;

    public User getRetailer() {
        return retailer;
    }

    public void setRetailer(User retailer) {
        this.retailer = retailer;
    }
}
