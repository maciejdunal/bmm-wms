package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ReleaseElements", schema = "Operations")
public class ReleaseElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*    @OneToOne
    @JoinColumn(name="operationId")
    private Release release;*/
    private Long operationId;
    private String operationType = "Release";

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
