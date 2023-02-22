package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Formulation;

import java.util.List;
import java.util.Optional;

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
     * Найти тип препарата по ID
     *
     * @param id Идентификатор типа препарата
     * @return Optional с найденным типом препарата. Пустой Optional, если ничего не найдено
     */
    Optional<Formulation> findById(int id);

    /**
     * Найти тип препарата по артикулу
     *
     * @param sku Артикул
     * @return Optional с найденным типом препарата. Пустой Optional, если ничего не найдено
     */
    Optional<Formulation> findBySku(String sku);

    /**
     * Добавить новый тип препарата в систему
     *
     * @param formulation Новый тип препарата
     * @return Сохраненный тип препарата
     */
    Formulation add(Formulation formulation);

    /**
     * Обновить данные типа препарата в системе
     *
     * @param formulation Тип препарата, данные которого необходимо обновить
     * @return Обновленный тип препарата
     */
    Formulation update(Formulation formulation);

    /**
     * Удалить тип препарата из системы
     *
     * @param formulation Тип препарата, который необходимо удалить
     */
    void delete(Formulation formulation);
}