package com.wsei.repository;

import com.wsei.model.Customer;
import com.wsei.model.CustomerContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerContactPersonRepository extends JpaRepository<CustomerContactPerson, Long> {
    Optional<CustomerContactPerson> findByName(String name);
    Optional<CustomerContactPerson> findById(Long id);
}
