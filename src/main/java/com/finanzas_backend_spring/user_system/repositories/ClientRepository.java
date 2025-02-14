package com.finanzas_backend_spring.user_system.repositories;

import com.finanzas_backend_spring.user_system.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository  extends JpaRepository<Client,Long> {
    Page<Client> findByUserId(Long userId, Pageable pageable);
    List<Client> findByActiveAndUserId(Boolean active, Long userId);
}
