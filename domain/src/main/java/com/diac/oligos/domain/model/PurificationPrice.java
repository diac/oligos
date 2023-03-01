package com.diac.oligos.domain.model;

import com.diac.oligos.domain.enumeration.BaseType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Модель данных "Цена типа очистки"
 */
@Entity
@Table(name = "purification_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class PurificationPrice {

    /**
     * Идентификатор цены типа очистки
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
     * Артикул типа очистки
     */
    @Column(name = "purification_sku")
    private String purificationSku;

    /**
     * Тип основания
     */
    @Column(name = "base_type")
    @Enumerated(EnumType.STRING)
    private BaseType baseType;

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