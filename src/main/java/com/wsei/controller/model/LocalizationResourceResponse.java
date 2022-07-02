package com.wsei.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalizationResourceResponse {

    private Long id;
    private Long warehouseId;
    private Long quantity;
    private Long articleId;
    private Long localizationId;
    @Digits(integer=6, fraction=2)
    private BigDecimal weight;
}
