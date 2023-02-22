package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.ModificationCategory;
import com.diac.oligos.domain.enumeration.ModificationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Модель данных "Модификатор"
 */
@Entity
@Table(name = "modification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Modification {

    /**
     * Идентификатор модификатора
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ElementCollection(targetClass = ModificationCategory.class)
    @CollectionTable(
            name = "modification_modification_category",
            joinColumns = {@JoinColumn(name = "modification_id")}
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "modification_category")
    private Set<ModificationCategory> modificationCategories;

    /**
     * Типы модификатора
     */
    @ElementCollection(targetClass = ModificationType.class)
    @CollectionTable(
            name = "modification_modification_type",
            joinColumns = {@JoinColumn(name = "modification_id")}
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "modification_type")
    private Set<ModificationType> modificationTypes;

    /**
     * Доступные типы очистки
     */
    @ManyToMany
    @JoinTable(
            name = "modification_purification",
            joinColumns = {@JoinColumn(name = "modification_id")},
            inverseJoinColumns = {@JoinColumn(name = "purification_id")}
    )
    private Set<Purification> availablePurifications;

    /**
     * Артикул модификатора
     */
    private String sku;
}