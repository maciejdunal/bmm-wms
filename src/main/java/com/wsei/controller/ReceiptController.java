package com.wsei.controller;

import com.wsei.controller.model.CustomerUpdateRequest;
import com.wsei.controller.model.ReceiptResponse;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.model.Customer;
import com.wsei.model.Receipt;
import com.wsei.model.Warehouse;
import com.wsei.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    private ReceiptResponse mapToResponse(Receipt receipt) {

        Warehouse warehouse = receipt.getWarehouse();
        Customer customer = receipt.getCustomer();

        return ReceiptResponse.builder()
                .id(receipt.getId())
                .operationType(receipt.getOperationType())
                .documentNumber(receipt.getDocumentNumber())
                .creationDate(receipt.getCreationDate())
                .modificationDate(receipt.getModificationDate())
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .customerId(Objects.nonNull(customer) ? customer.getId() : null)
                .userId(receipt.getUser().getId())
                .description(receipt.getDescription())
                .build();
    }


    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receipts")
    public List<ReceiptResponse> getReceipts(){
        return receiptService.getReceipts()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receipts/{id}")
    public ReceiptResponse getReceipt(@PathVariable Long id)
    {
        return mapToResponse(receiptService.getReceipt(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/receipts")
    public ReceiptResponse addReceipt(@Valid @RequestBody Receipt receipt){
        return mapToResponse(receiptService.saveReceipt(receipt));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receipts/{id}")
    public ReceiptResponse updateReceipt(@RequestBody Receipt newReceipt, @PathVariable Long id){
        return mapToResponse(receiptService.updateReceipt(newReceipt, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/receipts/{id}")
    void deleteReceipt(@PathVariable Long id){
        receiptService.deleteReceipt(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receipts/add-warehouse-to-receipt")
    public ReceiptResponse updateReceiptWarehouse(@RequestBody WarehouseUpdateRequest request){
        Receipt receipt = receiptService.assignWarehouse(request);

        return mapToResponse(receipt);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receipts/add-customer-to-receipt")
    public ReceiptResponse updateReceiptCustomer(@RequestBody CustomerUpdateRequest request){
        Receipt receipt = receiptService.assignCustomer(request);

        return mapToResponse(receipt);
    }
}
