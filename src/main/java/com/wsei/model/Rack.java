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
@Table(name="Racks", schema = "Warehouses")
public class Rack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="rowId")
    private Row row;
    
    @Size(max = 3)
    private String name;
    @Digits(integer=8, fraction=2)
    private BigDecimal capacity;
    @OneToOne
    @JoinColumn(name="warehouseId")
    private Warehouse warehouse;
}
