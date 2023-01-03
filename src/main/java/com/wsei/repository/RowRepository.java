package com.wsei.repository;

import com.wsei.model.Row;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RowRepository extends JpaRepository<Row, Long> {
    Optional<Row> findByName(String name);

    Optional<Row> findById(Long id);
}
