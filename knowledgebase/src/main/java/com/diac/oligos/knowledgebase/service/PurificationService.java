package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Purification;

import java.util.List;

/**
 * Сервис для работы с объектами модели Purification
 */
public interface PurificationService {

    /**
     * Найти все типы очистки
     *
     * @return Список типов очистки
     */
    List<Purification> findAll();

    /**
     * Найти тип очистки по ID
     *
     * @param id Идентификатор типа очистки
     * @return Тип очистки
     */
    Purification findById(int id);

    /**
     * Найти тип очистки по артикулу
     *
     * @param sku Артикул типа очистки
     * @return Тип очистки
     */
    Purification findBySku(String sku);

    /**
     * Добавить новый тип очистки в систему
     *
     * @param purification Новый тип очистки
     * @return Сохраненный тип очистки
     */
    Purification add(Purification purification);

    /**
     * Обновить данные типа очистки в системе
     *
     * @param id           Идентификатор типа очистки, данные которого необходимо обновить
     * @param purification Объект с обновленными данными типа очистки
     * @return Обновленный тип очистки
     */
    Purification update(int id, Purification purification);

    /**
     * Удалить тип очистки из системы
     *
     * @param id Идентификатор типа очистки, который необходимо удалить
     */
    void delete(int id);
}