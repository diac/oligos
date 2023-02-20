package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Тип препарата"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Formulation {

    /**
     * Идентификатор типа препарата
     */
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