package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Modification;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами модели Modification
 */
public interface ModificationService {

    /**
     * Найти все модификаторы
     *
     * @return Список модификаторов
     */
    List<Modification> findAll();

    /**
     * Найти модификатор по ID
     *
     * @param id Идентификатор модификатора
     * @return Optional с найденным модификатором. Пустой Optional, если ничего не найдено
     */
    Optional<Modification> findById(int id);

    /**
     * Найти модификатор по артикулу
     *
     * @param sku Артикул модификатора
     * @return Optional с найденным модификатором. Пустой Optional, если ничего не найдено
     */
    Optional<Modification> findBySku(String sku);

    /**
     * Добавить новый модификатор в систему
     *
     * @param modification Новый модификатор
     * @return Сохраненный модификатор
     */
    Modification add(Modification modification);

    /**
     * Обновить данные модификатора в системе
     *
     * @param id Идентификатор модификатора, данные которого необходимо обновить
     * @param modification Объект с обновленными данными модификатора
     * @return Обновленный модификатор
     */
    Modification update(int id, Modification modification);

    /**
     * Удалить модификатор из системы
     *
     * @param id Идентификатор модификатора, который необходимо удалить
     */
    void delete(int id);
}