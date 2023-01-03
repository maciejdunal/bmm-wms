package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users", schema = "UAM")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Size(max = 32)
    private String username;

    @Size(min = 6, max = 255)
    private String password;

    @Size(min = 2, max = 24)
    private String name;

    @Size(min = 2, max = 24)
    private String surname;

    @OneToOne
    @JoinColumn(name = "roleId")
    private Role role;
}
