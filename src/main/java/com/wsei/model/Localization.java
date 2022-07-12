package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Localization", schema = "Warehouses")
public class Localization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 30)
    private String name;
    @Size(max = 8)
    private String displayName;
    
    private Long warehouseId;
    private Long capacity;

    @OneToOne
    @JoinColumn(name="placeId")
    private Place place ;
}
