package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.BaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Цена типа очистки"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurificationPrice {

    /**
     * Идентификатор цены типа очистки
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение цены
     */
    private int amount;

    /**
     * Тип очистки
     */
    private Purification purification;

    /**
     * Тип основания
     */
    private BaseType baseType;

    /**
     * Масштаб
     */
    private Scale scale;

    /**
     * Прейскурант
     */
    private PriceSchedule priceSchedule;
}