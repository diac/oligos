package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Scale;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами модели Scale
 */
public interface ScaleService {

    /**
     * Найти все масштабы
     *
     * @return Список масштабов
     */
    List<Scale> findAll();

    /**
     * Найти масштаб по ID
     *
     * @param id Идентификатор масштаба
     * @return Optional с найденным масштабом. Пустой Optional, если ничего не найдено
     */
    Optional<Scale> findById(int id);

    /**
     * Добавить новый масштаб в систему
     *
     * @param scale Новый масштаб
     * @return Сохраненный масштаб
     */
    Scale add(Scale scale);

    /**
     * Обновить данные масштаба в системе
     *
     * @param id Идентификатор масштаба, данные которого необходимо обновить
     * @param scale Объект с обновленными данными масштаба
     * @return Обновленный масштаб
     */
    Scale update(int id, Scale scale);

    /**
     * Удалить масштаб из системы
     *
     * @param id Идентификатор масштаба, который необходимо удалить
     */
    void delete(int id);
}