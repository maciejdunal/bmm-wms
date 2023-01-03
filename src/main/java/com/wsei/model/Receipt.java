package com.wsei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Receipts", schema = "Operations")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operationType = "Receipt";

    private String documentNumber = "r" + UUID.randomUUID().toString() + "t";
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @OneToOne
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
    @Size(max = 50)
    private String description;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

}
