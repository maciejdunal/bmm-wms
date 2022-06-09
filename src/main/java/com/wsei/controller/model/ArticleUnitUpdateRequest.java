package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleUnitUpdateRequest {
    String articleCode;
    String unitName;
}
