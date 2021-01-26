package com.finanzas_backend_spring.payments_system.models;

import com.finanzas_backend_spring.accounts_system.models.Account;
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
@Table(name = "trusts")
public class Trust {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price_product", precision = 8, scale = 2)
    private BigDecimal priceProduct;
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;
    @Column(name = "products", nullable = false)
    private String products;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
