package com.finanzas_backend_spring.accounts_system.models;

import com.finanzas_backend_spring.user_system.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "maintenance_id")
    private Maintenance maintenance;
    @ManyToOne
    @JoinColumn(name = "line_of_credit_id")
    private LineOfCredit lineOfCredit;
}
