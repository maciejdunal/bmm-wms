package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class NewWarehouseRequest {

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String displayName;

    @Digits(integer=8, fraction=2)
    private BigDecimal capacity;
}
