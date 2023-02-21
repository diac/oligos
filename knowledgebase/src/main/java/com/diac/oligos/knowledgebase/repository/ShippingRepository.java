package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов Shipping (Тип доставки)
 */
@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
}