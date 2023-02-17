package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.ModificationCategory;
import com.diac.oligos.domain.enumeration.ModificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Модель данных "Модификатор"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Modification {

    /**
     * Идентификатор модификатора
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя модификатора
     */
    private String name;

    /**
     * Код модификатора
     */
    private String code;

    /**
     * Категории модификатора
     */
    private Set<ModificationCategory> modificationCategories;

    /**
     * Типы модификатора
     */
    private Set<ModificationType> modificationTypes;
}