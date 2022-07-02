package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class NewLocalizationRequest {

    @Size(max = 30)
    private String name;
    @Size(max = 8)
    private String displayName;
    private Long capacity;

}
