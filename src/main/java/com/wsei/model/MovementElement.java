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
@Table(name="MovementElements", schema = "Operations")
public class MovementElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="id")
    private Receipt receipt;
    @OneToOne
    @JoinColumn(name="id")
    private OperationsType operationsType;
    @OneToOne
    @JoinColumn(name="id")
    private Article article;
    
    private Long userId;
    
    private Long quantity;
    
    @Digits(integer=6, fraction=2)
    private BigDecimal weight;
    @OneToOne
    @JoinColumn(name="localizationId",insertable=false, updatable=false)
    private Localization sourceLocalization;
    @OneToOne
    @JoinColumn(name="localizationId",insertable=false, updatable=false)
    private Localization targetLocalization;
    private Date creationDate;
    @OneToOne
    @JoinColumn(name="targetWarehouseId")
    private Warehouse targetWarehouse;
    @OneToOne
    @JoinColumn(name="sourceWarehouseId")
    private Warehouse sourceWarehouse;
}
