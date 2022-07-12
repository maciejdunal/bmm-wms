package com.wsei.service;

import com.wsei.controller.model.ArticleUpdateRequest;
import com.wsei.controller.model.LocalizationUpdateRequest;
import com.wsei.controller.model.OperationUpdateRequest;
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

@Service
@RequiredArgsConstructor
public class ReceiptElementService {

    private final ReceiptElementRepository repository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final LocalizationRepository localizationRepository;
    private final ArticleRepository articleRepository;
    private final ReceiptRepository receiptRepository;


    public List<ReceiptElement> getReceiptElements()
    {
        return repository.findAll();
    }

    public List<ReceiptElement> getReceiptElementsByOperationId(Long operationId)
    {
        return repository.findAllByOperationId(operationId);
    }
    public ReceiptElement getReceiptElement(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public ReceiptElement saveReceiptElement(ReceiptElement receiptElement)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username = authentication.getPrincipal();
        User currentUser = userRepository.findByUsername((String) username);
        LocalDateTime now = LocalDateTime.now();

        receiptElement.setUser(currentUser);
        receiptElement.setCreationDate(now);

        return repository.save(receiptElement);
    }

    public ReceiptElement updateReceiptElement(ReceiptElement newReceiptElement, Long id)
    {
        return repository.findById(id)
                .map (receiptElement -> {
                    receiptElement.setQuantity(newReceiptElement.getQuantity());
                    receiptElement.setWeight(newReceiptElement.getWeight());
                    return repository.save(receiptElement);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteReceiptElement(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    public ReceiptElement assignOperation(OperationUpdateRequest request) {
        ReceiptElement receiptElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Receipt receipt = receiptRepository.findById(request.getOperationId())
                .orElseThrow(() -> new NotFoundException(null));

        receiptElement.setOperationId(request.getOperationId());
/*        receiptElement.setReceipt(receipt);*/
        return repository.save(receiptElement);
    }

    public ReceiptElement assignArticle(ArticleUpdateRequest request) {
        ReceiptElement receiptElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new NotFoundException(null));

        receiptElement.setArticle(article);
        return repository.save(receiptElement);
    }

    public ReceiptElement assignWarehouse(WarehouseUpdateRequest request) {
        ReceiptElement receiptElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));

        receiptElement.setWarehouse(warehouse);
        return repository.save(receiptElement);
    }

    public ReceiptElement assignLocalization(LocalizationUpdateRequest request) {
        ReceiptElement receiptElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Localization localization = localizationRepository.findById(request.getLocalizationId())
                .orElseThrow(() -> new NotFoundException(null));

        receiptElement.setLocalization(localization);
        return repository.save(receiptElement);
    }
}
