package com.wsei.repository;

import com.wsei.model.Level;
import com.wsei.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByName(String name);
    Optional<Place> findById(Long id);
}
