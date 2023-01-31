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
    @Column(name = "secret_id")
    private String secretId;
    @Column(name = "secret_client")
    private String secretClient;

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
    @OneToMany(mappedBy = "retailer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Account> transferAccounts = new ArrayList<>();

    public void addTransferAccounts(Account transferAccount) {
        transferAccounts.add(transferAccount);
    }
    public void addRoles(Role role){ roles.add(role); }
}
