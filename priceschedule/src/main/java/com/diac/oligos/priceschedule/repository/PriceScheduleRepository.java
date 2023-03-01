package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.PriceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceScheduleRepository extends JpaRepository<PriceSchedule, Integer> {
}