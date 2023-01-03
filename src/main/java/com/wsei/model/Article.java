package com.wsei.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Articles", schema = "Products")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    private String name;

    @OneToOne
    @JoinColumn(name = "unitId")
    private Unit unit;

    @Digits(integer = 6, fraction = 2)
    private BigDecimal weight;

    @Size(max = 10)
    private String articleCode;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
