package com.wsei.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

    private Long id;

    @Size(max = 50)
    private String name;

    private String unitName;

    @Digits(integer=6, fraction=2)
    private BigDecimal weight;

    @Size(max = 10)
    private String articleCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime modificationDate;

    private Long userId;

}
