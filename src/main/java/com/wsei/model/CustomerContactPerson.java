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
@Table(name="CustomersContactPerson", schema = "Counterparties")
public class CustomerContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 50)
    private String name;
    
    @Size(max = 50)
    private String surname;
    
    @Size(max = 50)
    @Email
    private String email;
    
    @Size(max = 20)
    private String phoneNumber;
/*    @OneToOne
    @JoinColumn(name="name")
    private Customer customer;*/
}
