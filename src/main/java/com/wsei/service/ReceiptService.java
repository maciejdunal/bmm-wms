package com.wsei.service;

import com.wsei.controller.model.CustomerUpdateRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Customer;
import com.wsei.model.Receipt;
import com.wsei.model.User;
import com.wsei.model.Warehouse;
import com.wsei.repository.CustomerRepository;
import com.wsei.repository.ReceiptRepository;
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
public class ReceiptService {

    private final ReceiptRepository repository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;

    public List<Receipt> getReceipts() {
        return repository.findAll();
    }

    public Receipt getReceipt(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Receipt saveReceipt(Receipt receipt) {
        repository.findByDocumentNumber(receipt.getDocumentNumber())
                .ifPresent(existingReceipt -> {
                    String documentNumber = "r" + UUID.randomUUID().toString() + "t";
                    receipt.setDocumentNumber(documentNumber);
                });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username = authentication.getPrincipal();
        User currentUser = userRepository.findByUsername((String) username);
        LocalDateTime now = LocalDateTime.now();

        receipt.setUser(currentUser);
        receipt.setCreationDate(now);
        receipt.setModificationDate(now);

        return repository.save(receipt);
    }

    public Receipt updateReceipt(Receipt newReceipt, Long id) {
        return repository.findById(id)
                .map(receipt -> {

                    receipt.setDescription(newReceipt.getDescription());
                    receipt.setModificationDate(LocalDateTime.now());
                    return repository.save(receipt);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteReceipt(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public Receipt assignWarehouse(WarehouseUpdateRequest request) {
        Receipt receipt = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));

        receipt.setWarehouse(warehouse);
        receipt.setModificationDate(LocalDateTime.now());
        return repository.save(receipt);
    }

    public Receipt assignCustomer(CustomerUpdateRequest request) {
        Receipt receipt = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException(null));

        receipt.setModificationDate(LocalDateTime.now());
        receipt.setCustomer(customer);
        return repository.save(receipt);
    }
}
