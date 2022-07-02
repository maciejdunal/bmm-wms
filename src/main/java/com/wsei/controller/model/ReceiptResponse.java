package com.wsei.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponse {


    private Long id;
    private String operationType;
    private String documentNumber;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime modificationDate;

    private Long warehouseId;
    private Long customerId;
    private String description;
    private Long userId;
}
