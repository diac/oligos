package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.SynthesisPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов SynthesisPrice (Цена синтеза)
 */
@Repository
public interface SynthesisPriceRepository extends JpaRepository<SynthesisPrice, Integer> {

    /**
     * Найти цену синтеза по значению артикула
     *
     * @param sku Значение артикула
     * @return Optional с найденной ценой. Пустой Optional, если ничего не найдено
     */
    Optional<SynthesisPrice> findBySynthesisSku(String sku);
}