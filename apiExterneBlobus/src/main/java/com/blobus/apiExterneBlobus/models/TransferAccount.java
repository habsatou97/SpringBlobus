package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String encryptedPinCode;
    private WalletType walletType;
    boolean is_active;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User retailer;

    @OneToMany(mappedBy = "retailerTransferAccount")
    @JsonIgnore
    List<Transaction> retailerTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "customerTransferAccount")
    @JsonIgnore
    List<Transaction> customerTransactions = new ArrayList<>();

    public void addRetailerTransactions(Transaction transaction){
        retailerTransactions.add(transaction);
    }

    public void addCustomerTransactions(Transaction transaction){
        customerTransactions.add(transaction);
    }

}
