package com.wsei.controller.model;

import com.wsei.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    @Email
    @Size(max = 32)
    private String username;
    @Size(min = 6, max = 255)
    private String name;
    @Size(min = 2, max = 24)
    private String surname;
    private String roleName;
}
