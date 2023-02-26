package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Тип очистки"
 */
@Entity
@Table(name = "purification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Purification {

    /**
     * Идентификатор типа очистки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование типа очистки
     */
    @NotNull(message = "Purification name is required")
    @NotBlank(message = "Purification name cannot be blank")
    private String name;

    /**
     * Артикул типа очистки
     */
    @NotNull(message = "Purification SKU is required")
    @NotBlank(message = "Purification SKU cannot be blank")
    private String sku;
}