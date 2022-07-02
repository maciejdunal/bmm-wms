package com.wsei.repository;

import com.wsei.model.Level;
import com.wsei.model.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByName(String name);
    Optional<Level> findById(Long id);
}
