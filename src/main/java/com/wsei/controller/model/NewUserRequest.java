package com.wsei.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class NewUserRequest {

    @Email
    @Size(max = 32)
    private String username;
    @Size(min = 6, max = 255)
    private String password;
    @Size(min = 2, max = 24)
    private String name;
    @Size(min = 2, max = 24)
    private String surname;
}
