package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Цена типа препарата"
 */
@Entity
@Table(name = "formulation_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class FormulationPrice {

    /**
     * Идентификатор цены типа препарата
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение цены
     */
    @NotNull(message = "Price amount is required")
    @Min(value = 0, message = "Price must be greater or equal to zero")
    private int amount;

    /**
     * Артикул типа препарата
     */
    @Column(name = "formulation_sku")
    @NotNull(message = "Formulation SKU is required")
    @NotBlank(message = "Formulation SKU cannot be blank")
    private String formulationSku;

    /**
     * Прейскурант
     */
    @ManyToOne
    @JoinColumn(name = "price_schedule_id")
    @NotNull(message = "Associated Price Schedule is required")
    private PriceSchedule priceSchedule;
}