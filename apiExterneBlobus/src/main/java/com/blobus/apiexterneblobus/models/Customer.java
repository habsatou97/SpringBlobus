package com.blobus.apiexterneblobus.models;



import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ablaye Faye
 */
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
