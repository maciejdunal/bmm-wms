package com.wsei.repository;

import com.wsei.model.Receipt;
import com.wsei.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
    Optional<Receipt> findByDocumentNumber(String documentNumber);

    Optional<Release> findById(Long id);
}
