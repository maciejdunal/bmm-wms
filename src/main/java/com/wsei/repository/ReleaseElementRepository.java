package com.wsei.repository;

import com.wsei.model.ReleaseElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReleaseElementRepository extends JpaRepository<ReleaseElement, Long> {
    List<ReleaseElement> findAllByOperationId(Long operationId);
}
