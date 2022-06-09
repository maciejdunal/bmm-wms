package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="OperationsHistory", schema = "Operations")
public class OperationsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//operationId czyli Id jakiejs operacji
    @OneToOne
    @JoinColumn(name="id")
    private OperationsType operationsType;
    @OneToOne
    @JoinColumn(name="id")
    private Article article;
    
    private Long userId;
    
    private Long quantity;
//sourcelocalization
    //target localization
    //source warehousse
    //target warehouse
    @OneToOne
    @JoinColumn(name="id")
    private Customer customer;
    private Date creationDate;
}

