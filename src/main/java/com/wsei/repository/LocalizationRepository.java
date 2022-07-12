package com.wsei.repository;

import com.wsei.model.Localization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocalizationRepository extends JpaRepository<Localization, Long> {
    Optional<Localization> findByName(String name);
    Optional<Localization> findById(Long id);
    List<Localization> findAllByWarehouseId(Long id);
}
