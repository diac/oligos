package com.diac.oligos.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
@Builder
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
    @NotNull(message = "Shipping name is required")
    @NotBlank(message = "Shipping name cannot be blank")
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