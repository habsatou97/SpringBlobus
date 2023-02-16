package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
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
            nullable = false)
    @Email
    private String email;
    private List<Role> roles = new ArrayList<>();
    private String ninea;

    @Column(
            name = "phone_number",
            nullable = false,
            length = 9
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
    @OneToMany(mappedBy = "retailer")
    private List<Bulk> bulks = new ArrayList<>();

    public void addTransferAccounts(Account transferAccount) {
        accounts.add(transferAccount);
    }

    public void addRoles(Role role){ roles.add(role);}
    public void addBulks(Bulk bulk){ bulks.add(bulk); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.name()));
        if (roles != null){
            for (Role role: roles
            ) {
                if (!role.name().equals(Role.USER.name())){
                    grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
                }
            }
        }

        return grantedAuthorities;
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
