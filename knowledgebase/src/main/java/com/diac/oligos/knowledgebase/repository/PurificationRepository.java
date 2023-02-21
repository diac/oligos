package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Purification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов Purification (Тип очистки)
 */
@Repository
public interface PurificationRepository extends JpaRepository<Purification, Integer> {

    /**
     * Найти тип очистки по значению артикула
     *
     * @param sku Значение артикула
     * @return Optional с найденным типом очистки. Пустой Optional, если ничего не найдено
     */
    Optional<Purification> findBySku(String sku);
}