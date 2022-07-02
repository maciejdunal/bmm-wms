package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.LocalizationResource;
import com.wsei.model.MovementElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovementElementRepository extends JpaRepository<MovementElement, Long> {
/*    Optional<MovementElement> findByArticleCode(String articleCode);*/
 /*   Optional<MovementElement> findByName(String name);*/
    Optional<MovementElement> findById(Long id);
}
