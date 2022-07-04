package com.wsei.repository;

import com.wsei.model.OperationsAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationsAuditRepository extends JpaRepository<OperationsAudit, Long> {
/*        Optional<OperationsAudit> findByArticle(Article article);*/
/*        Optional<OperationsHistory> findByName(String name);
        Optional<OperationsHistory> findById(Long id);*/
}
