package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.BaseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Синтез"
 */
@Entity
@Table(name = "synthesis")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Synthesis {

    /**
     * Идентификатор синтеза
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование синтеза
     */
    private String name;

    /**
     * Тип основания
     */
    @Column(name = "base_type")
    @Enumerated(EnumType.STRING)
    private BaseType baseType;

    /**
     * Артикул синтеза
     */
    private String sku;
}