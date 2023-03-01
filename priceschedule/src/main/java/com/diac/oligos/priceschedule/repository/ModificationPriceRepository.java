package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.ModificationPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов ModificationPrice (Цена модификатора)
 */
@Repository
public interface ModificationPriceRepository extends JpaRepository<ModificationPrice, Integer> {

    /**
     * Найти цену модификатора по значению артикула
     *
     * @param sku Значение артикула
     * @return Optional с найденной ценой. Пустой Optional, если ничего не найдено
     */
    Optional<ModificationPrice> findByModificationSku(String sku);
}