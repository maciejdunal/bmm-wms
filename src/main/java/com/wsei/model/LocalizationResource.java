package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="LocalizationResources", schema = "Warehouses")
public class LocalizationResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="warehouseId")
    private Warehouse warehouse;
    
    private Long quantity;
/*    private String articleCode;*/
    @OneToOne
    @JoinColumn(name="articleId")
    private Article article;
    @OneToOne
    @JoinColumn(name="localizationId")
    private Localization localization;
    @Digits(integer=6, fraction=2)
    private BigDecimal weight;
}
