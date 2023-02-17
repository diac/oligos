package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.Base;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Элемент последовательности"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SequenceItem {

    /**
     * Идентификатор элемента последовательности
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Основание
     */
    private Base base;

    /**
     * Модификатор
     */
    private Modification modification;

    /**
     * Порядковый номер элемента в последовательности
     */
    private long sequenceItemOrder;
}