package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.Place;
import com.wsei.model.ReceiptElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptElementRepository extends JpaRepository<ReceiptElement, Long> {
    Optional<ReceiptElement> findByArticle(Article article);
/*    Optional<ReceiptElement> findByName(String name);
    Optional<ReceiptElement> findById(Long id);*/
}
