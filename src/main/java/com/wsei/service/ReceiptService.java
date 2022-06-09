package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Receipt;
import com.wsei.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final ReceiptRepository repository;

    public List<Receipt> getReceipts(){
        return repository.findAll();
    }

    public Receipt getReceipt(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
    public Receipt saveReceipt(Receipt receipt){
        repository.findByDocumentNumber(receipt.getDocumentNumber())
                .ifPresent(existingReceipt -> {
                    throw new AlreadyExistException();
                });
        return repository.save(receipt);
    }

    public Receipt updateReceipt(Receipt newReceipt, Long id)
    {
        return repository.findById(id)
                .map (receipt -> {
                    receipt.setOperationsType(newReceipt.getOperationsType());
                    receipt.setWarehouse(newReceipt.getWarehouse());
                    receipt.setCustomer(newReceipt.getCustomer());
                    receipt.setDescription(newReceipt.getDescription());
                    return saveReceipt(receipt);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteReceipt(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
