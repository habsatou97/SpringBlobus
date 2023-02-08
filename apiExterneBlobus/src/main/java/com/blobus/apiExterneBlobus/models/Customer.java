package com.blobus.apiExterneBlobus.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true, length = 9)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Account> transferAccounts = new ArrayList<>();

   

    public void addTransferAccounts(Account account){ transferAccounts.add(account); }
}
