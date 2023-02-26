package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.BaseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Синтез"
 */
@Entity
@Table(name = "synthesis")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Synthesis {

    /**
     * Идентификатор синтеза
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование синтеза
     */
    @NotNull(message = "Synthesis name is required")
    @NotBlank(message = "Synthesis name cannot be blank")
    private String name;

    /**
     * Тип основания
     */
    @Column(name = "base_type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Base type is required for Synthesis")
    private BaseType baseType;

    /**
     * Артикул синтеза
     */
    @NotNull(message = "Synthesis SKU is required")
    @NotBlank(message = "Synthesis SKU cannot be blank")
    private String sku;
}