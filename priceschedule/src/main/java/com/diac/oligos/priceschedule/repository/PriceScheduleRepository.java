package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.PriceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов PriceSchedule (Прейскурант)
 */
@Repository
public interface PriceScheduleRepository extends JpaRepository<PriceSchedule, Integer> {

    /**
     * Найти первый действующий прейскурант в репозитории
     *
     * @return Optional с найденным прейскурантом. Пустой Optional, если ничего не найдено
     */
    Optional<PriceSchedule> findFirstByEffectiveTrue();
}