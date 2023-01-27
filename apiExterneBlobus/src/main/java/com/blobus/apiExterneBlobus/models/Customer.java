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
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @NonNull
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
}
