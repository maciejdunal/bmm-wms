package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Movements", schema = "Operations")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operationType = "Movement";
    private String documentNumber = "m" + UUID.randomUUID().toString() + "t";
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @OneToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Size(max = 50)
    private String description;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "targetWarehouseId")
    private Warehouse targetWarehouse;

    @OneToOne
    @JoinColumn(name = "sourceWarehouseId")
    private Warehouse sourceWarehouse;

}
