package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WarehouseUpdateRequest {
    Long resourceId;
    Long warehouseId;
}
