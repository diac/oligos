package com.diac.oligos.knowledgebase.service;

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
     * Найти тип препарата по ID
     *
     * @param id Идентификатор типа препарата
     * @return Тип препарата
     */
    Formulation findById(int id);

    /**
     * Найти тип препарата по артикулу
     *
     * @param sku Артикул
     * @return Тип препарата
     */
    Formulation findBySku(String sku);

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
     * @param id          Идентификатор препарата, данные которого необходимо обновить
     * @param formulation Объект с обновленными данными препарата
     * @return Обновленный тип препарата
     */
    Formulation update(int id, Formulation formulation);

    /**
     * Удалить тип препарата из системы
     *
     * @param id Идентификатор препарата, который необходимо удалить
     */
    void delete(int id);
}