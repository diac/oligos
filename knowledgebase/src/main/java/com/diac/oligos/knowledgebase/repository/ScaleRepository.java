package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Scale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения объектов Scale (Масштаб)
 */
@Repository
public interface ScaleRepository extends JpaRepository<Scale, Integer> {
}