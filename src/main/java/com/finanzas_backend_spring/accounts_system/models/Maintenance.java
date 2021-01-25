package com.finanzas_backend_spring.accounts_system.models;

import com.finanzas_backend_spring.user_system.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "maintenances")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", precision = 8, scale = 2)
    private BigDecimal price;
    @Column(name = "frequency", nullable = false)
    private String frequency;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
