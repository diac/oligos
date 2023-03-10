package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Shipping;

import java.util.List;

/**
 * Сервис для работы с объектами модели Shipping
 */
public interface ShippingService {

    /**
     * Найти все типы доставки
     *
     * @return Список типов доставки
     */
    List<Shipping> findAll();

    /**
     * Найти тип доставки по ID
     *
     * @param id Идентификатор типа доставки
     * @return Тип доставки
     */
    Shipping findById(int id);

    /**
     * Добавить новый тип доставки в систему
     *
     * @param shipping Новый тип доставки
     * @return Сохраненный тип доставки
     */
    Shipping add(Shipping shipping);

    /**
     * Обновить данные типа доставки в системе
     *
     * @param id       Идентификатор типа доставки, данные которого необходимо обновить
     * @param shipping Объект с обновленными данными типа доставки
     * @return Обновленный тип доставки
     */
    Shipping update(int id, Shipping shipping);

    /**
     * Удалить тип доставки из системы
     *
     * @param id Идентификатор типа доставки, который необходимо удалить
     */
    void delete(int id);
}