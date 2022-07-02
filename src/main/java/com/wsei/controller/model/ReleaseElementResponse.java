package com.wsei.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseElementResponse {

    private Long id;
    private Long operationId;
    private String operationType;
    private Long articleId;
    private Long userId;
    private Long quantity;
    private BigDecimal weight;
    private Long localizationId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;
    private Long warehouseId;
}
