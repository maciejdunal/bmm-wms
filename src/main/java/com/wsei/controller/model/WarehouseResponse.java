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
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {

    private Long id;
    @Size(max = 50)
    private String name;
    @Size(max = 50)
    private String displayName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime modificationDate;
    @Digits(integer=8, fraction=2)
    private BigDecimal capacity;
}
