package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Модель данных "Масштаб"
 */
@Entity
@Table(name = "scale")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Scale {

    /**
     * Идентификатор масштаба
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение в наномолях
     */
    private int nanomols;

    /**
     * Отображаемое наименование
     */
    private String displayName;
}