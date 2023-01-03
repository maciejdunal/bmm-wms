package com.wsei.repository;

import com.wsei.model.ReceiptElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptElementRepository extends JpaRepository<ReceiptElement, Long> {
    List<ReceiptElement> findAllByOperationId(Long operationId);
}
