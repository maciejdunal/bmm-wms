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
@Table(name="ReleaseElements", schema = "Operations")
public class ReleaseElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="id")
    private Release release;
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
    @JoinColumn(name="id")
    private Localization localization;
    private Date creationDate;
    @OneToOne
    @JoinColumn(name="id")
    private Warehouse warehouse;
}
