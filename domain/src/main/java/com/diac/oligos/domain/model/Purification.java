package com.diac.oligos.domain.model;

import jakarta.persistence.*;
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
    private String name;

    /**
     * Артикул типа очистки
     */
    private String sku;
}