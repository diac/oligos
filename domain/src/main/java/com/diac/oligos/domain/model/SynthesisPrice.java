package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Цена синтеза"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SynthesisPrice {

    /**
     * Идентификатор цены синтеза
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение цены
     */
    private int amount;

    /**
     * Артикул синтеза
     */
    private String synthesisSku;

    /**
     * Масштаб
     */
    private Scale scale;

    /**
     * Прейскурант
     */
    private PriceSchedule priceSchedule;
}