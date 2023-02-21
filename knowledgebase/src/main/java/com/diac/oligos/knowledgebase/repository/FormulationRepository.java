package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Formulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов Formulation (Тип препарата)
 */
@Repository
public interface FormulationRepository extends JpaRepository<Formulation, Integer> {

    /**
     * Найти тип препарата по значению артикула
     *
     * @param sku Значение артикула
     * @return Optional с найденным типом препарата. Пустой Optional, если ничего не найдено
     */
    Optional<Formulation> findBySku(String sku);
}