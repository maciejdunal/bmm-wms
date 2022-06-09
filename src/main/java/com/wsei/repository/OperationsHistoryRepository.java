package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.OperationsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationsHistoryRepository extends JpaRepository<OperationsHistory, Long> {
        Optional<OperationsHistory> findByArticle(Article article);
}
