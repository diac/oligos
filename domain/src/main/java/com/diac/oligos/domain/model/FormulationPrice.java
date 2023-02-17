package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Цена типа препарата"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FormulationPrice {

    /**
     * Идентификатор цены типа препарата
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение цены
     */
    private int amount;

    /**
     * Тип препарата
     */
    private Formulation formulation;

    /**
     * Прейскурант
     */
    private PriceSchedule priceSchedule;
}