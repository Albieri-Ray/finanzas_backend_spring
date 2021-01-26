package com.finanzas_backend_spring.accounts_system.repositories;

import com.finanzas_backend_spring.accounts_system.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByClientId (Long clientId);

}
