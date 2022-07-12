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

    private Long articleId;
    private Long userId;
    private Long quantity;

    private Long localizationId;
    private Long sourceLocalizationId;
    private Long targetLocalizationId;
    private Long warehouseId;
    private Long sourceWarehouseId;
    private Long targetWarehouseId;
    private Long customerId;

    private LocalDateTime creationDate;
/*    private LocalDateTime modificationDate;*/
}

