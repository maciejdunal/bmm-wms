package com.wsei.repository;

import com.wsei.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    Optional<Movement> findByDocumentNumber(String documentNumber);

    Optional<Movement> findById(Long id);
}
