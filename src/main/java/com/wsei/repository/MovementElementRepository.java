package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.LocalizationResource;
import com.wsei.model.MovementElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovementElementRepository extends JpaRepository<MovementElement, Long> {
    List<MovementElement> findAllByOperationId(Long operationId);

    Optional<MovementElement> findById(Long id);
}
