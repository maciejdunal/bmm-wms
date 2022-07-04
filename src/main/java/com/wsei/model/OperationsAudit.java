package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

// Prevent changes from being applied by Hibernate
@Immutable
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="View04_OperationsAudit")
public class OperationsAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;
/*    @ManyToOne
    @JoinColumn(name="operationId")*/
    private Long operationId;
    private String operationType;

    @OneToOne
    @JoinColumn(name="articleId")

    private Article article;
    private Long userId;
    private Long quantity;

    @OneToOne
    @JoinColumn(name="localizationId")
    private Localization localization;

    @OneToOne
    @JoinColumn(name="sourceLocalizationId")
    private Localization sourceLocalization;

    @OneToOne
    @JoinColumn(name="targetLocalizationId")
    private Localization targetLocalization;

    @OneToOne
    @JoinColumn(name="warehouseId")

    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name="sourceWarehouseId")
    private Warehouse sourceWarehouse;

    @OneToOne
    @JoinColumn(name="targetWarehouseId")
    private Warehouse targetWarehouse;

    @OneToOne
    @JoinColumn(name="customerId")
    private Customer customer;

    private LocalDateTime creationDate;
/*    private LocalDateTime modificationDate;*/
}

