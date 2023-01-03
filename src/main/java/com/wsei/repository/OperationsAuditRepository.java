package com.wsei.repository;

import com.wsei.model.OperationsAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationsAuditRepository extends JpaRepository<OperationsAudit, Long> {
}
