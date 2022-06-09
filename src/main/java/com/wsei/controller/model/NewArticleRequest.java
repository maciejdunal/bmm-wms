package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class NewArticleRequest {


    @Size(max = 50)
    private String name;

    @Digits(integer=6, fraction=2)
    private BigDecimal weight;

    @Size(max = 10)
    private String articleCode;

}
