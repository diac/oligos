package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Synthesis;

import java.util.List;
import java.util.Optional;

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
     * @return Optional с найденным синтезом. Пустой Optional, если ничего не найдено
     */
    Optional<Synthesis> findById(int id);

    /**
     * Найти синтез по артикулу
     *
     * @param sku Артикул
     * @return Optional с найденным синтезом. Пустой Optional, если ничего не найдено
     */
    Optional<Synthesis> findBySku(String sku);

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
     * @param synthesis Синтез, данные которого необходимо обновить
     * @return Обновленный синтез
     */
    Synthesis update(Synthesis synthesis);

    /**
     * Удалить синтез из системы
     *
     * @param synthesis Синтез, который необходимо удалить
     */
    void delete(Synthesis synthesis);
}