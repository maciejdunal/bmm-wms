package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.ReleaseElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReleaseElementRepository extends JpaRepository<ReleaseElement, Long> {
    Optional<ReleaseElement> findByArticle(Article article);
}
