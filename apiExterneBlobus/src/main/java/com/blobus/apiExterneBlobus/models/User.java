package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
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

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "retailer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    public User(Long id,String firstName, String lastName, String email, Role role, String ninea, String phoneNumber, String userId, String userSecret) {
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.roles= Collections.singletonList(Role.valueOf(String.valueOf(role)));
        this.ninea=ninea;
        this.phoneNumber=phoneNumber;
        this.userId=userId;
        this.userSecret=userSecret;
    }
  //  public User(){}

    public void addTransferAccounts(Account transferAccount) {
        accounts.add(transferAccount);
    }

    public void addRoles(Role role){ roles.add(role);}
}
