package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.ModificationCategory;
import com.diac.oligos.domain.enumeration.ModificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Modification name is required")
    @NotBlank(message = "Modification name cannot be blank")
    private String name;

    /**
     * Код модификатора
     */
    @NotNull(message = "Modification code is required")
    @NotBlank(message = "Modification code cannot be blank")
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
    @NotNull(message = "Modification SKU is required")
    @NotBlank(message = "Modification SKU cannot be blank")
    private String sku;
}