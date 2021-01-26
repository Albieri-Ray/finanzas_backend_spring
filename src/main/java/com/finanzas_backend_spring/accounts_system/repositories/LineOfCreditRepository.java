package com.finanzas_backend_spring.accounts_system.repositories;

import com.finanzas_backend_spring.accounts_system.models.LineOfCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineOfCreditRepository extends JpaRepository<LineOfCredit,Long> {

}
