package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.BaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных "Синтез"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Synthesis {

    /**
     * Идентификатор синтеза
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование синтеза
     */
    private String name;

    /**
     * Тип основания
     */
    private BaseType baseType;
}