package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Scale nanomols value is required")
    @Min(value = 1, message = "Scale nanomols value has to be greater or equal to 1")
    private int nanomols;

    /**
     * Отображаемое наименование
     */
    @Column(name = "display_name")
    @NotNull(message = "Scale display name is required")
    @NotBlank(message = "Scale display name cannot be blank")
    private String displayName;
}