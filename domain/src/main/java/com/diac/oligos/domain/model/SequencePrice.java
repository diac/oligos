package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Цена цепочки"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SequencePrice {

    /**
     * Идентификатор цены цепочки
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Цепочка
     */
    private Sequence sequence;

    /**
     * Значение цены
     */
    private int amount;

    /**
     * Заметки по расчету цены в свободном формате
     */
    private String notes;
}