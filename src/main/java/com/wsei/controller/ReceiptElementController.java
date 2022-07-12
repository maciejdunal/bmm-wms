package com.wsei.controller;

import com.wsei.controller.model.*;
import com.wsei.model.*;
import com.wsei.service.ReceiptElementService;
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
public class ReceiptElementController {

    private final ReceiptElementService receiptElementService;

    private ReceiptElementResponse mapToResponse(ReceiptElement receiptElement) {

/*        Receipt operation = receiptElement.getReceipt();*/
        Warehouse warehouse = receiptElement.getWarehouse();
        Article article= receiptElement.getArticle();
        Localization localization = receiptElement.getLocalization();
        return ReceiptElementResponse.builder()
                .id(receiptElement.getId())
/*                .operationId(Objects.nonNull(operation) ? operation.getId() : null)*/
                .operationId(receiptElement.getOperationId())
                .operationType(receiptElement.getOperationType())
                .articleId(Objects.nonNull(article) ? article.getId() : null)
                .userId(receiptElement.getUser().getId())
                .quantity(receiptElement.getQuantity())
                .weight(receiptElement.getWeight())
                .localizationId(Objects.nonNull(localization) ? localization.getId() : null)
                .creationDate(receiptElement.getCreationDate())
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)

                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receiptElements")
    public List<ReceiptElementResponse> getReceiptElements() {
        return receiptElementService.getReceiptElements()
                .stream()
                .map(this::mapToResponse)
//                .map(article -> mapToResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receiptElements/operationId/{operationId}")
    public List<ReceiptElementResponse> getReceiptElementsByOperationId(@PathVariable Long operationId) {
        return receiptElementService.getReceiptElementsByOperationId(operationId)
                .stream()
                .map(this::mapToResponse)
//                .map(article -> mapToResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/receiptElements/{id}")
    public ReceiptElementResponse getReceiptElements(@PathVariable Long id)
    {
        return mapToResponse(receiptElementService.getReceiptElement(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/receiptElements")
    public ReceiptElementResponse addReceiptElement(@Valid @RequestBody ReceiptElement receiptElement)
    {
        return mapToResponse(receiptElementService.saveReceiptElement(receiptElement));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receiptElements/{id}")
    public ReceiptElementResponse updateReceiptElement(@RequestBody ReceiptElement newReceiptElement, @PathVariable Long id)
    {
        return mapToResponse(receiptElementService.updateReceiptElement(newReceiptElement, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/receiptElements/{id}")
    void deleteReceiptElements(@PathVariable Long id)
    {
        receiptElementService.deleteReceiptElement(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receiptElements/add-operation-to-receiptElement")
    public ReceiptElementResponse updateReceiptElementOperation(@RequestBody OperationUpdateRequest request){
        ReceiptElement receiptElement = receiptElementService.assignOperation(request);

        return mapToResponse(receiptElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receiptElements/add-article-to-receiptElement")
    public ReceiptElementResponse updateReceiptElementArticle(@RequestBody ArticleUpdateRequest request){
        ReceiptElement receiptElement = receiptElementService.assignArticle(request);

        return mapToResponse(receiptElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receiptElements/add-warehouse-to-receiptElement")
    public ReceiptElementResponse updateReceiptElementWarehouse(@RequestBody WarehouseUpdateRequest request){
        ReceiptElement receiptElement = receiptElementService.assignWarehouse(request);

        return mapToResponse(receiptElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/receiptElements/add-localization-to-receiptElement")
    public ReceiptElementResponse updateReceiptElementLocalization(@RequestBody LocalizationUpdateRequest request){
        ReceiptElement receiptElement = receiptElementService.assignLocalization(request);

        return mapToResponse(receiptElement);
    }

}
