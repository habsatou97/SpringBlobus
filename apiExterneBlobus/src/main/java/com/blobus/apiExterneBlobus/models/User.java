package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.RETAILER.name()));
    }

    @Override
    public String getPassword() {
        return userSecret;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
