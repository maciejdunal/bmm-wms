package com.wsei.repository;

import com.wsei.model.Receipt;
import com.wsei.model.ReceiptElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findByDocumentNumber(String documentNumber);
    Optional<Receipt> findById(Long id);
}
