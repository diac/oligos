package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Цена модификации"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ModificationPrice {

    /**
     * Идентификатор цены модификации
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение цена
     */
    private int amount;

    /**
     * Артикул модификатора
     */
    private String modificationSku;

    /**
     * Масштаб
     */
    private Scale scale;

    /**
     * Прейскурант
     */
    private PriceSchedule priceSchedule;
}