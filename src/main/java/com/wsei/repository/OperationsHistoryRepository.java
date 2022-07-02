package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.Movement;
import com.wsei.model.OperationsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationsHistoryRepository extends JpaRepository<OperationsHistory, Long> {
        Optional<OperationsHistory> findByArticle(Article article);
/*        Optional<OperationsHistory> findByName(String name);
        Optional<OperationsHistory> findById(Long id);*/
}
