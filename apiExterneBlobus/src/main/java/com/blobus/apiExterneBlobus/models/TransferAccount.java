package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    @Column(nullable = false, unique = true)
    private String encryptedPinCode;
    private WalletType walletType;
    boolean is_active;


}
