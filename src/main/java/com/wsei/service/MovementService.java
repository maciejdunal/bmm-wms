package com.wsei.service;

import com.wsei.controller.model.CustomerUpdateRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.NotFoundException;
import com.wsei.model.*;
import com.wsei.repository.*;
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
public class MovementService {

    private final MovementRepository repository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;

    public List<Movement> getMovements(){
        return repository.findAll();
    }

    public Movement getMovement(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
    public Movement saveMovement(Movement movement){
        repository.findByDocumentNumber(movement.getDocumentNumber())
                .ifPresent(existingMovement -> {
                    /*throw new AlreadyExistException()*/
                    String documentNumber =  "m" + UUID.randomUUID().toString() + "t";
                    movement.setDocumentNumber(documentNumber);
                });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username = authentication.getPrincipal();
        User currentUser = userRepository.findByUsername((String) username);
        LocalDateTime now = LocalDateTime.now();

        movement.setUser(currentUser);
        movement.setCreationDate(now);
        movement.setModificationDate(now);

        return repository.save(movement);
    }

    public Movement updateMovement(Movement newMovement, Long id)
    {
        return repository.findById(id)
                .map (movement -> {

                    movement.setDescription(newMovement.getDescription());
                    movement.setModificationDate(LocalDateTime.now());
                    return repository.save(movement);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteMovement(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    public Movement assignWarehouse(WarehouseUpdateRequest request) {
        Movement movement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));

        movement.setSourceWarehouse(warehouse);
        movement.setTargetWarehouse(warehouse);
        movement.setModificationDate(LocalDateTime.now());
        return repository.save(movement);
    }

    public Movement assignTargetWarehouse(WarehouseUpdateRequest request) {
        Movement movement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));

        movement.setTargetWarehouse(warehouse);
        movement.setModificationDate(LocalDateTime.now());
        return repository.save(movement);
    }

    public Movement assignCustomer(CustomerUpdateRequest request) {
        Movement movement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException(null));

        movement.setModificationDate(LocalDateTime.now());
        movement.setCustomer(customer);
        return repository.save(movement);
    }
}
