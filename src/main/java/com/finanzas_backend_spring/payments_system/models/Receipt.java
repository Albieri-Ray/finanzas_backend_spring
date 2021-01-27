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
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "interest", precision = 8, scale = 2)
    private BigDecimal interest;
    @Column(name = "quantity_of_days")
    private Integer quantityOfDays;
    @Column(name = "maintenance_cost", precision = 8, scale = 2)
    private BigDecimal maintenanceCost;
    @Column(name = "credit_consumed", precision = 8, scale = 2)
    private BigDecimal creditConsumed;
    @Column(name = "total", precision = 8, scale = 2)
    private BigDecimal total;
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
