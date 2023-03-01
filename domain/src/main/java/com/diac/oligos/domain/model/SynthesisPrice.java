package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Модель данных "Цена синтеза"
 */
@Entity
@Table(name = "synthesis_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class SynthesisPrice {

    /**
     * Идентификатор цены синтеза
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Значение цены
     */
    private int amount;

    /**
     * Артикул синтеза
     */
    @Column(name = "synthesis_sku")
    private String synthesisSku;

    /**
     * Масштаб в наномолях
     */
    @Column(name = "scale_nanomols")
    private int scaleNanomols;

    /**
     * Прейскурант
     */
    @ManyToOne
    @JoinColumn(name = "price_schedule_id")
    private PriceSchedule priceSchedule;
}