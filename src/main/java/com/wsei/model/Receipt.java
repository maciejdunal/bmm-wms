package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Receipts", schema = "Operations")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name="id")
    private OperationsType operationsType;
    
    @Size(max = 15)
    private String documentNumber;
    private Date creationDate = new Date();
    private Date modificationDate = new Date();
    @OneToOne
    @JoinColumn(name="id")
    private Warehouse warehouse;
    
    @OneToOne
    @JoinColumn(name="id")
    private Customer customer;
    @Size(max = 50)
    private String description;
    
    @OneToOne
    @JoinColumn(name="id")
    private User user;

}
