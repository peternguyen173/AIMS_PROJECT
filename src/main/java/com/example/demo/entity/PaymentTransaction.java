package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "payment_transaction")
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String errorCode;
    private String transactionId;
    private String transactionContent;
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Date createdAt;
}
