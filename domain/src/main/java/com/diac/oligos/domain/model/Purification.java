package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Тип очистки"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Purification {

    /**
     * Идентификатор типа очистки
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование типа очистки
     */
    private String name;
}