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
@Table(name="Places", schema = "Warehouses")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="rowId")
    private Row row;
    @ManyToOne
    @JoinColumn(name="rackId")
    private Rack rack;
    @ManyToOne
    @JoinColumn(name="levelId")
    private Level level;
    
    @Size(max = 1)
    private String name;
    @Digits(integer=8, fraction=2)
    private BigDecimal capacity;
    @ManyToOne
    @JoinColumn(name="warehouseId")
    private Warehouse warehouse;
}
