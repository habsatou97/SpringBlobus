package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enumerations.WalletType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "transferAccounts")
@Data
@RequiredArgsConstructor
@AllArgsConstructor


public class TransferAccount {
    @Id
    @GeneratedValue
    private Long id;
    private double balance;
    private int encryptedPinCode;
    private WalletType walletType;
    boolean is_active;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
