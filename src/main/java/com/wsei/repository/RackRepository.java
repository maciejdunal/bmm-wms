package com.wsei.repository;

import com.wsei.model.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RackRepository extends JpaRepository<Rack, Long> {
    Optional<Rack> findByName(String name);
    Optional<Rack> findById(Long id);
}
