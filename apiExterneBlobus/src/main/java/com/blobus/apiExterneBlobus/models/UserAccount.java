package com.blobus.apiExterneBlobus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_accounts")
@RequiredArgsConstructor
@Data
@AllArgsConstructor
@Getter
@Setter
public class UserAccount {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String userSecret;



}
