package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.Place;
import com.wsei.model.Receipt;
import com.wsei.model.ReceiptElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiptElementRepository extends JpaRepository<ReceiptElement, Long> {
   List<ReceiptElement> findAllByOperationId(Long operationId);
/*    Optional<ReceiptElement> findByName(String name);
    Optional<ReceiptElement> findById(Long id);*/
}
