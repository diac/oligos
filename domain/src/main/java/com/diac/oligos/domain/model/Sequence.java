package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.BaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Модель данных "Цепочка"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sequence {

    /**
     * Идентификатор цепочки
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Тип оснований в цепочке
     */
    private BaseType baseType;

    /**
     * Модификатор в начале цепочки
     */
    private Modification headModification;

    /**
     * Последовательность элементов цепочки (основание + модификатор)
     */
    private List<SequenceItem> sequenceItems;

    /**
     * Модификатор в конце цепочки
     */
    private Modification tailModification;

    /**
     * Температура плавления
     */
    private int meltingTemperature;

    /**
     * Энтальпия
     */
    private int enthalpy;

    /**
     * Энтропия
     */
    private int entropy;

    /**
     * Молекулярная масса
     */
    private float molecularMass;

    /**
     * Свободная энергия Гиббса
     */
    private int deltaG;
}