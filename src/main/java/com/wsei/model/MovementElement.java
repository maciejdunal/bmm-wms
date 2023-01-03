package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MovementElements", schema = "Operations")
public class MovementElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long operationId;
    private String operationType = "Movement";

    @OneToOne
    @JoinColumn(name = "articleId")
    private Article article;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private Long quantity;

    @Digits(integer = 6, fraction = 2)
    private BigDecimal weight;

    @OneToOne
    @JoinColumn(name = "sourceLocalizationId")
    private Localization sourceLocalization;

    @OneToOne
    @JoinColumn(name = "targetLocalizationId")
    private Localization targetLocalization;

    private LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "sourceWarehouseId")
    private Warehouse sourceWarehouse;

    @OneToOne
    @JoinColumn(name = "targetWarehouseId")
    private Warehouse targetWarehouse;
}
