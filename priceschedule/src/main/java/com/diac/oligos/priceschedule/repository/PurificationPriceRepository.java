package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.PurificationPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов PurificationPrice (Цена типа очистки)
 */
@Repository
public interface PurificationPriceRepository extends JpaRepository<PurificationPrice, Integer> {

    /**
     * Найти цену типа очистки по значению артикула
     *
     * @param sku Значение артикула
     * @return Optional с найденной ценой. Пустой Optional, если ничего не найдено
     */
    Optional<PurificationPrice> findByPurificationSku(String sku);
}