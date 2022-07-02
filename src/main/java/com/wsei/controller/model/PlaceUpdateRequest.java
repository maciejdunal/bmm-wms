package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaceUpdateRequest {
    Long resourceId;
    Long placeId;
}
