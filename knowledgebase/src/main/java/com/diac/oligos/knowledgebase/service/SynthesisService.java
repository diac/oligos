package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Synthesis;

import java.util.List;

/**
 * Сервис для работы с объектами модели Synthesis
 */
public interface SynthesisService {

    /**
     * Найти все синтезы
     *
     * @return Список синтезов
     */
    List<Synthesis> findAll();

    /**
     * Найти синтез по ID
     *
     * @param id Идентификатор синтеза
     * @return Синтез
     */
    Synthesis findById(int id);

    /**
     * Найти синтез по артикулу
     *
     * @param sku Артикул
     * @return Синтез
     */
    Synthesis findBySku(String sku);

    /**
     * Добавить новый синтез в систему
     *
     * @param synthesis Новый синтез
     * @return Сохраненный синтез
     */
    Synthesis add(Synthesis synthesis);

    /**
     * Обновить данные синтеза в системе
     *
     * @param id        Идентификатор синтеза, данные которого необходимо обновить
     * @param synthesis Объект с обновленными данными синтеза
     * @return Обновленный синтез
     */
    Synthesis update(int id, Synthesis synthesis);

    /**
     * Удалить синтез из системы
     *
     * @param id Идентификатор синтеза, который необходимо удалить
     */
    void delete(int id);
}