package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Тип препарата"
 */
@Entity
@Table(name = "formulation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Formulation {

    /**
     * Идентификатор типа препарата
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование типа препарата
     */
    private String name;

    /**
     * Признак доступности типа препарата
     */
    private boolean available;

    /**
     * Артикул типа препарата
     */
    private String sku;
}