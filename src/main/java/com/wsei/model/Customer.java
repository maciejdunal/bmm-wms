package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Customers", schema = "Counterparties")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 200)
    private String name;
    
    @Size(max = 50)
    private String street;
    
    @Size(max = 50)
    private String city;
    
    @Size(max = 10)
    private String postalCode;
    
    @Size(max = 30)
    private String country;
    
    @Size(max = 50)
    @Email
    private String email;
    
    @Size(max = 20)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name="contactPersonId")
    private CustomerContactPerson customerContactPerson;

}
