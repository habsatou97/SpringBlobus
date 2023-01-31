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
    private List<Role> roles = new ArrayList<>();
    private String ninea;

    @Column(
            name = "phone_number"
    )
    private String phoneNumber;
    @Column(unique = true)
    private String userId;
    private String userSecret;

   // hello

    @OneToMany(mappedBy = "retailer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    public void addTransferAccounts(Account transferAccount) {
        accounts.add(transferAccount);
    }

    public void addRoles(Role role){ roles.add(role);}
}
