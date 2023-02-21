package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Модель данных "Тип доставки"
 */
@Entity
@Table(name = "shipping")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Shipping {

    /**
     * Идентификатор типа доставки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование типа доставки
     */
    private String name;

    /**
     * Типы препаратов
     */
    @ManyToMany
    @JoinTable(
            name = "shipping_formulation",
            joinColumns = {@JoinColumn(name = "shipping_id")},
            inverseJoinColumns = {@JoinColumn(name = "formulation_id")}
    )
    private Set<Formulation> formulations;
}