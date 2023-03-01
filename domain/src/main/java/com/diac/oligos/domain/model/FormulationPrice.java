package com.diac.oligos.domain.model;

import jakarta.persistence.*;
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
    private int amount;

    /**
     * Артикул типа препарата
     */
    @Column(name = "formulation_sku")
    private String formulationSku;

    /**
     * Прейскурант
     */
    @ManyToOne
    @JoinColumn(name = "price_schedule_id")
    private PriceSchedule priceSchedule;
}