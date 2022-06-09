package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerContactPersonUpdateRequest {
    Long customerId;
    Long customerContactPersonId;
}
