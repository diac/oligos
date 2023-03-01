package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.FormulationPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов FormulationPrice (Цена типа препарата)
 */
@Repository
public interface FormulationPriceRepository extends JpaRepository<FormulationPrice, Integer> {

    /**
     * Найти цену типа препарата по значению артикула
     *
     * @param sku Значение артикула
     * @return Optional с найденной ценой. Пустой Optional, если ничего не найдено
     */
    Optional<FormulationPrice> findByFormulationSku(String sku);
}