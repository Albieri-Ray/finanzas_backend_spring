package com.finanzas_backend_spring.upon_delivery_system.models;


import com.finanzas_backend_spring.user_system.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price_product", precision = 8, scale = 2)
    private BigDecimal priceProduct;
    @Column(name = "price_delivery", precision = 8, scale = 2)
    private BigDecimal priceDelivery;
    @Column(name = "register_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registerDate;
    @Column(name = "frequency", nullable = false)
    private String frequency;
    @Column(name = "home_address", nullable = false)
    private String homeAddress;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}


