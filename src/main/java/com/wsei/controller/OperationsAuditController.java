package com.wsei.controller;

import com.wsei.controller.model.OperationsAuditResponse;
import com.wsei.model.OperationsAudit;
import com.wsei.service.OperationsAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OperationsAuditController {

    private final OperationsAuditService operationsAuditService;

    private OperationsAuditResponse mapToResponse(OperationsAudit operationsAudit) {

        return OperationsAuditResponse.builder()
                .uuid(operationsAudit.getUuid())
                .operationId(operationsAudit.getOperationId())
                .operationType(operationsAudit.getOperationType())
                .articleId(operationsAudit.getArticleId())
                .userId(operationsAudit.getUserId())
                .quantity(operationsAudit.getQuantity())
                .localizationId(operationsAudit.getLocalizationId())
                .sourceLocalizationId(operationsAudit.getSourceLocalizationId())
                .targetLocalizationId(operationsAudit.getTargetLocalizationId())
                .creationDate(operationsAudit.getCreationDate())
                .warehouseId(operationsAudit.getWarehouseId())
                .sourceWarehouseId(operationsAudit.getSourceWarehouseId())
                .targetWarehouseId(operationsAudit.getTargetWarehouseId())
                .build();
    }
    
    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/operationsAudit")
    public List<OperationsAuditResponse> getOperationAudit(){
        return operationsAuditService.getOperationsAudit()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

}
