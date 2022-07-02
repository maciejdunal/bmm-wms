package com.wsei.repository;

import com.wsei.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByName(String name);
    Optional<Article> findById(Long id);
    Optional<Article> findByArticleCode(String articleCode);
}
