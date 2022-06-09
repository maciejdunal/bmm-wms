package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.MovementElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovementElementRepository extends JpaRepository<MovementElement, Long> {
    Optional<MovementElement> findByArticle(Article article);
}
