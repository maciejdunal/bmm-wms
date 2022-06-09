package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class NewRowRequest {

    @Size(max = 1)
    private String name;
    @Digits(integer=8, fraction=2)
    private BigDecimal capacity;
}
