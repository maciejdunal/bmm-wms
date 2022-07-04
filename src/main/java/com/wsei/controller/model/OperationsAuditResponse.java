package com.wsei.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wsei.model.Article;
import com.wsei.model.Customer;
import com.wsei.model.Localization;
import com.wsei.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationsAuditResponse {

    private Long uuid;
    private Long operationId;
    private String operationType;
    private Long articleId;
    private Long userId;
    private Long quantity;
    private Long localizationId;
    private Long sourceLocalizationId;
    private Long targetLocalizationId;
    private Long warehouseId;
    private Long sourceWarehouseId;
    private Long targetWarehouseId;
    private Long customerId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime modificationDate;
}
