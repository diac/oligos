package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Совместимость модификатора и типа очистки"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ModificationPurificationCompatibility {

    /**
     * Идентификатор связи
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Модификатор
     */
    private Modification modification;

    /**
     * Тип очистки
     */
    private Purification purification;
}