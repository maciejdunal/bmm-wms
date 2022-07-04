package com.wsei.controller;

import com.wsei.controller.model.MovementElementResponse;
import com.wsei.controller.model.OperationsAuditResponse;
import com.wsei.model.*;
import com.wsei.service.OperationsAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OperationsAuditController {

    private final OperationsAuditService operationsAuditService;

    private OperationsAuditResponse mapToResponse(OperationsAudit operationsAudit) {

        //Movement operation = operationsAudit.getMovement();
        Warehouse warehouse = operationsAudit.getSourceWarehouse();
        Warehouse sourceWarehouse = operationsAudit.getSourceWarehouse();
        Warehouse targetWarehouse = operationsAudit.getTargetWarehouse();
        Article article= operationsAudit.getArticle();
        Localization localization = operationsAudit.getLocalization();
        Localization sourceLocalization = operationsAudit.getSourceLocalization();
        Localization targetLocalization = operationsAudit.getTargetLocalization();
        return OperationsAuditResponse.builder()
                .uuid(operationsAudit.getUuid())
                .operationId(operationsAudit.getOperationId())
                .operationType(operationsAudit.getOperationType())
                .articleId(Objects.nonNull(article) ? article.getId() : null)
                .userId(operationsAudit.getUserId())
                .quantity(operationsAudit.getQuantity())
                .localizationId(Objects.nonNull(localization) ? localization.getId() : null)
                .sourceLocalizationId(Objects.nonNull(sourceLocalization) ? sourceLocalization.getId() : null)
                .targetLocalizationId(Objects.nonNull(targetLocalization) ? targetLocalization.getId() : null)
                .creationDate(operationsAudit.getCreationDate())
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .sourceWarehouseId(Objects.nonNull(sourceWarehouse) ? sourceWarehouse.getId() : null)
                .targetWarehouseId(Objects.nonNull(targetWarehouse) ? targetWarehouse.getId() : null)

                .build();
    }
    
    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/operationsAudit")
    public List<OperationsAuditResponse> getOperationAudit(){
        return operationsAuditService.getOperationsAudit()
                .stream()
                .map(this::mapToResponse)
//                .map(article -> mapToResponse(article))
                .collect(Collectors.toList());
    }

}
