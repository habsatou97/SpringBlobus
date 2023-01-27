package com.blobus.apiExterneBlobus.models;

import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
import com.blobus.apiExterneBlobus.models.enums.TransactionStatus;
import com.blobus.apiExterneBlobus.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reference;
    private Date createdDate;
    private Boolean receiveNotificatiion;
    private Date requestDate;
    private TransactionStatus status;
    private TransactionType type;
    private Double montant;
    private TransactionCurrency currency;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
    private Customer customer;


}
