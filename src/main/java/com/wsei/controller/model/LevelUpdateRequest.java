package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LevelUpdateRequest {
    Long resourceId;
    Long levelId;
}
