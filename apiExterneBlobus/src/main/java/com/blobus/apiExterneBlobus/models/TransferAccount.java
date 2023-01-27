package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transferAccounts")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User retailer;

}
