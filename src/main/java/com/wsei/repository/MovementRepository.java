package com.wsei.repository;

import com.wsei.model.Customer;
import com.wsei.model.Movement;
import com.wsei.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    Optional<Movement> findByDocumentNumber(String documentNumber);
}
