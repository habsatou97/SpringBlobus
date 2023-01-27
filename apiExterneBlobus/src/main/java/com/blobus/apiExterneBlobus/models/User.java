package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    @Column(
            name = "email",
            unique = true,
            nullable = false
    )
    private String email;
    private List<Role> roles;

    @Column(
            name = "phone_number",
            unique = true,
            nullable = false
    )
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "retailer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TransferAccount> transferAccounts = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();


    public void addTransactions(Transaction transaction) {
       transactions.add(transaction);
    }

    public void addTransferAccounts(TransferAccount transferAccount) {
        transferAccounts.add(transferAccount);
    }
}
