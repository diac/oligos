package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Масштаб"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Scale {

    /**
     * Идентификатор масштаба
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение в наномолях
     */
    private int nanomols;

    /**
     * Отображаемое наименование
     */
    private String displayName;
}