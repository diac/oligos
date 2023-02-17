package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Тип доставки"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Shipping {

    /**
     * Идентификатор типа доставки
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование типа доставки
     */
    private String name;

    /**
     * Тип препарата
     */
    private Formulation formulation;
}