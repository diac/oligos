package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Synthesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов Synthesis (Синтез)
 */
@Repository
public interface SynthesisRepository extends JpaRepository<Synthesis, Integer> {

    /**
     * Найти синтез по значению артикула
     *
     * @param sku Значение артикула
     * @return Optional с найденным синтезом. Пустой Optional, если ничего не найдено
     */
    Optional<Synthesis> findBySku(String sku);
}