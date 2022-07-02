package com.wsei.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalizationResponse {

    private Long id;
    @Size(max = 30)
    private String name;
    @Size(max = 8)
    private String displayName;
    private Long warehouseId;
    private Long capacity;
    private Long placeId ;
}
