package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer shippingFees;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMedia> lstOrderMedia = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "delivery_info", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "info_key")
    @Column(name = "info_value")
    private HashMap<String, String> deliveryInfo = new HashMap<>();
}
