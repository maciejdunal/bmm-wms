package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RowUpdateRequest {
    Long resourceId;
    Long rowId;
}
