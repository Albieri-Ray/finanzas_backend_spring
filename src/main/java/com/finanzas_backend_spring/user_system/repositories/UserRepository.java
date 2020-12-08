package com.finanzas_backend_spring.user_system.repositories;

import com.finanzas_backend_spring.user_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User,Long>  {

}
