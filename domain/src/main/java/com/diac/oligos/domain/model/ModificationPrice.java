package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных "Цена модификации"
 */
@Entity
@Table(name = "modification_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ModificationPrice {

    /**
     * Идентификатор цены модификации
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
     * Артикул модификатора
     */
    @Column(name = "modification_sku")
    @NotNull(message = "Modification SKU is required")
    @NotBlank(message = "Modification SKU cannot be blank")
    private String modificationSku;

    /**
     * Масштаб в наномолях
     */
    @Column(name = "scale_nanomols")
    @NotNull(message = "Scale value is required")
    @Min(value = 1, message = "Scale value must be greater or equal to 1")
    private int scaleNanomols;

    /**
     * Прейскурант
     */
    @ManyToOne
    @JoinColumn(name = "price_schedule_id")
    @NotNull(message = "Associated Price Schedule is required")
    private PriceSchedule priceSchedule;
}