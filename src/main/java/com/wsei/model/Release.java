package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Releases", schema = "Operations")
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operationType = "Relaese";

    private String documentNumber = "r" + UUID.randomUUID().toString() + "e";
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @OneToOne
    @JoinColumn(name="warehouseId")
    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name="customerId")
    private Customer customer;
    @Size(max = 50)
    private String description;

    @OneToOne
    @JoinColumn(name="userId")
    private User user;
}
