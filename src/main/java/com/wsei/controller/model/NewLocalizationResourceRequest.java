package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class NewLocalizationResourceRequest {

    private Long quantity;
    @Digits(integer=6, fraction=2)
    private BigDecimal weight;

}
