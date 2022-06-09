package com.wsei.controller;

import com.wsei.model.Receipt;
import com.wsei.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receipts")
    public ResponseEntity<List<Receipt>> getReceipts(){
        return ResponseEntity.ok().body(receiptService.getReceipts());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receipts/{id}")
    public Receipt getReceipt(@PathVariable Long id)
    {
        return receiptService.getReceipt(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/receipts")
    public Receipt addReceipt(@Valid @RequestBody Receipt receipt){
        return receiptService.saveReceipt(receipt);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receipts/{id}")
    public Receipt updateReceipt(@RequestBody Receipt newReceipt, @PathVariable Long id){
        return receiptService.updateReceipt(newReceipt, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/receipts/{id}")
    void deleteReceipt(@PathVariable Long id){
        receiptService.deleteReceipt(id);
    }
}
