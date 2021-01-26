package com.finanzas_backend_spring.accounts_system.models;

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
@Table(name = "line_of_credits")
public class LineOfCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rate_type", nullable = false)
    private String rateType;
    @Column(name = "credit", precision = 8, scale = 2)
    private BigDecimal credit;
    @Column(name = "type_of_currency", nullable = false)
    private String typeOfCurrency;
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;
    @Column(name = "period", nullable = false)
    private String period;
    @Column(name = "rate_value", precision = 8, scale = 2)
    private BigDecimal rateValue;
}
