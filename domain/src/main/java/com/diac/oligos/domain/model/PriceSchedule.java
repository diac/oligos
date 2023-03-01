package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Модель данных "Прейскурант"
 */
@Entity
@Table(name = "price_schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PriceSchedule {

    /**
     * Идентификатор прейскуранта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя прейскуранта
     */
    @NotNull(message = "Price Schedule name is required")
    @NotBlank(message = "Price Schedule name cannot be blank")
    private String name;

    /**
     * Дата создания прейскуранта
     */
    private LocalDateTime created;

    /**
     * Признак действительности прейскуранта
     */
    private boolean effective;
}