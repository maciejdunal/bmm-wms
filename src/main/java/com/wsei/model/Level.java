package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Levels", schema = "Warehouses")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "rowId")
    private Row row;

    @OneToOne
    @JoinColumn(name = "rackId")
    private Rack rack;

    @Size(max = 1)
    private String name;

    @Digits(integer = 8, fraction = 2)
    private BigDecimal capacity;

    @OneToOne
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;
}
