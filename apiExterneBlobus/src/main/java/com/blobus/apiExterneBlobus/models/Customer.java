package com.blobus.apiExterneBlobus.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @NonNull
    private String email;
    @Column(nullable = false, unique = false)
    private String phoneNumber;
}
