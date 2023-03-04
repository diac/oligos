package com.diac.oligos.admin.service;

import com.diac.oligos.domain.model.Formulation;

import java.util.List;

/**
 * Сервис для работы с объектами модели Formulation
 */
public interface FormulationService {

    /**
     * Найти все типы препаратов
     *
     * @return Список с типами препаратов
     */
    List<Formulation> findAll();

    /**
     * Добавить новый тип препарата в систему
     *
     * @param formulation Новый тип препарата
     * @return Сохраненный тип препарата
     */
    Formulation add(Formulation formulation);
}