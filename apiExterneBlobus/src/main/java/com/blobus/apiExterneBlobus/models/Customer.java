package com.blobus.apiExterneBlobus.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String firstName;

    private String lastName;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    @Column(nullable = false, unique = true, length = 9)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Account> transferAccounts = new ArrayList<>();

   

    public void addTransferAccounts(Account account){ transferAccounts.add(account); }
}
