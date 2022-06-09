package com.wsei.controller;

import com.wsei.model.ReceiptElement;
import com.wsei.service.ReceiptElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReceiptElementController {

    private final ReceiptElementService receiptElementService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receiptElements")
    public ResponseEntity<List<ReceiptElement>> getReceiptElements() {
        return ResponseEntity.ok().body(receiptElementService.getReceiptElements());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receiptElements/{id}")
    public ReceiptElement getReceiptElements(@PathVariable Long id)
    {
        return receiptElementService.getReceiptElement(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/receiptElements")
    public ReceiptElement addReceiptElement(@Valid @RequestBody ReceiptElement receiptElement)
    {
        return receiptElementService.saveReceiptElement(receiptElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receiptElements/{id}")
    public ReceiptElement updateReceiptElement(@RequestBody ReceiptElement newReceiptElement, @PathVariable Long id)
    {
        return receiptElementService.updateReceiptElement(newReceiptElement, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/receiptElements/{id}")
    void deleteReceiptElements(@PathVariable Long id)
    {
        receiptElementService.deleteReceiptElement(id);
    }

}
