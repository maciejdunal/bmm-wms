package com.wsei.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RackResponse {

    private Long id;
    private Long rowId;
    @Size(max = 3)
    private String name;
    @Digits(integer=8, fraction=2)
    private BigDecimal capacity;
    private Long warehouseId;
}
