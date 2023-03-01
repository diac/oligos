package com.diac.oligos.domain.model;

import jakarta.persistence.*;
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
     * Значение цена
     */
    private int amount;

    /**
     * Артикул модификатора
     */
    @Column(name = "modification_sku")
    private String modificationSku;

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