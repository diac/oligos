package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Modification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов Modification (Модификатор)
 */
@Repository
public interface ModificationRepository extends JpaRepository<Modification, Integer> {

    /**
     * Найти модификатор по значению артикула
     *
     * @param sku Значение артикула
     * @return Optional с найденным модификаторром. Пустой Optional, если ничего не найдено
     */
    Optional<Modification> findBySku(String sku);
}