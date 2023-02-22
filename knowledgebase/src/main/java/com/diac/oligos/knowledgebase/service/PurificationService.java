package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Purification;

import java.util.List;
import java.util.Optional;

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
     * @return Optional с найденным типом очистки. Пустой Optional, если ничего не найдено
     */
    Optional<Purification> findById(int id);

    /**
     * Найти тип очистки по артикулу
     *
     * @param sku Артикул типа очистки
     * @return Optional с найденным типом очистки. Пустой Optional, если ничего не найдено
     */
    Optional<Purification> findBySku(String sku);

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
     * @param purification Тип очистки, данные которого необходимо обновить
     * @return Обновленный тип очистки
     */
    Purification update(Purification purification);

    /**
     * Удалить тип очистки из системы
     *
     * @param purification Тип очистки, который необходимо удалить
     */
    void delete(Purification purification);
}