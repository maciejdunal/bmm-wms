package com.wsei.service;

import com.wsei.controller.model.CustomerUpdateRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.*;
import com.wsei.repository.CustomerRepository;
import com.wsei.repository.ReleaseRepository;
import com.wsei.repository.UserRepository;
import com.wsei.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final ReleaseRepository repository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;


    public List<Release> getReleases()
    {
        return repository.findAll();
    }
    public Release getRelease(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Release saveRelease(Release release)
    {
        repository.findByDocumentNumber(release.getDocumentNumber())
                .ifPresent(existingReceipt -> {
                    /*throw new AlreadyExistException()*/
                    String documentNumber =  "r" + UUID.randomUUID().toString() + "e";
                    release.setDocumentNumber(documentNumber);
                });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username = authentication.getPrincipal();
        User currentUser = userRepository.findByUsername((String) username);
        LocalDateTime now = LocalDateTime.now();

        release.setUser(currentUser);
        release.setCreationDate(now);
        release.setModificationDate(now);

        return repository.save(release);
    }

    public Release updateRelease(Release newRelease, Long id)
    {
        return repository.findById(id)
                .map (release -> {
                    release.setDescription(newRelease.getDescription());
                    release.setModificationDate(LocalDateTime.now());

                    return repository.save(release);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteRelease(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    public Release assignWarehouse(WarehouseUpdateRequest request) {
        Release release = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));

        release.setWarehouse(warehouse);
        release.setModificationDate(LocalDateTime.now());
        return repository.save(release);
    }

    public Release assignCustomer(CustomerUpdateRequest request) {
        Release release = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException(null));

        release.setModificationDate(LocalDateTime.now());
        release.setCustomer(customer);
        return repository.save(release);
    }
}
