package com.wsei.repository;

import com.wsei.model.LocalizationResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalizationResourcesRepository extends JpaRepository<LocalizationResource, Long> {
/*    Optional<LocalizationResource> findByArticleCode(String articleCode);*/
/*    Optional<LocalizationResource> findByName(String name);*/
    Optional<LocalizationResource> findById(Long id);
}
