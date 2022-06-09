package com.wsei.repository;

import com.wsei.model.OperationsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationsTypeRepository extends JpaRepository<OperationsType, Long> {
    Optional<OperationsType> findByName(String name);
}
