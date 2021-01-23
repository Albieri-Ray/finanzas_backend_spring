package com.finanzas_backend_spring.upon_delivery_system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "frequency", nullable = false)
    private String frequency;
    @Column(name = "total", precision = 8, scale = 2)
    private BigDecimal total;
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
}
