package com.wsei.repository;

import com.wsei.model.Article;
import com.wsei.model.Receipt;
import com.wsei.model.Release;
import com.wsei.model.ReleaseElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReleaseElementRepository extends JpaRepository<ReleaseElement, Long> {
    List<ReleaseElement> findAllByOperationId(Long operationId);
/*    Optional<ReleaseElement> findByName(String name);
    Optional<ReleaseElement> findById(Long id);*/
}
