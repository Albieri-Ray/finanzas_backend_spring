package com.finanzas_backend_spring.payments_system.repositories;

import com.finanzas_backend_spring.payments_system.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {
    @Query("SELECT r FROM Receipt r JOIN Account a ON r.account.id = a.id JOIN Client c ON a.client.id = c.id WHERE c.id =: clientId")
    List<Receipt> findByClientId (@Param("clientId") Long clientId);
}
