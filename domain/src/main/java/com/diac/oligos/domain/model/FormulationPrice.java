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
     * Артикул типа препарата
     */
    private String formulationSku;

    /**
     * Прейскурант
     */
    private PriceSchedule priceSchedule;
}