package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Тип препарата"
 */
@Entity
@Table(name = "formulation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Formulation {

    /**
     * Идентификатор типа препарата
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование типа препарата
     */
    @NotNull(message = "Formulation name is required")
    @NotBlank(message = "Formulation name cannot be blank")
    private String name;

    /**
     * Признак доступности типа препарата
     */
    private boolean available;

    /**
     * Артикул типа препарата
     */
    @NotNull(message = "Formulation SKU is required")
    @NotBlank(message = "Formulation SKU cannot be blank")
    private String sku;
}