package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ReceiptElements", schema = "Operations")
public class ReceiptElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long operationId;
    private String operationType = "Receipt";

    @OneToOne
    @JoinColumn(name="articleId")
    private Article article;

    @OneToOne
    @JoinColumn(name="userId")
    private User user;

    private Long quantity;

    @Digits(integer=6, fraction=2)
    private BigDecimal weight;

    @OneToOne
    @JoinColumn(name="localizationId")
    private Localization localization;

    private LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name="warehouseId")
    private Warehouse warehouse;
}
