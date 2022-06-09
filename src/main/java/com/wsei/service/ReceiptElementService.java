package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.ReceiptElement;
import com.wsei.repository.ReceiptElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptElementService {

    private final ReceiptElementRepository repository;


    public List<ReceiptElement> getReceiptElements()
    {
        return repository.findAll();
    }
    public ReceiptElement getReceiptElement(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public ReceiptElement saveReceiptElement(ReceiptElement receiptElement)
    {
        repository.findByArticle(receiptElement.getArticle())
                .ifPresent(existingReceiptElement -> {
                    throw new AlreadyExistException();
                });

        return repository.save(receiptElement);
    }

    public ReceiptElement updateReceiptElement(ReceiptElement newReceiptElement, Long id)
    {
        return repository.findById(id)
                .map (receiptElement -> {
                    receiptElement.setReceipt(newReceiptElement.getReceipt());
                    receiptElement.setOperationsType(newReceiptElement.getOperationsType());
                    receiptElement.setArticle(newReceiptElement.getArticle());
                    receiptElement.setUserId(newReceiptElement.getUserId());
                    receiptElement.setQuantity(newReceiptElement.getQuantity());
                    receiptElement.setWeight(newReceiptElement.getWeight());
                    receiptElement.setLocalization(newReceiptElement.getLocalization());
                    receiptElement.setWarehouse(newReceiptElement.getWarehouse());

                    return repository.save(receiptElement);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteReceiptElement(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
