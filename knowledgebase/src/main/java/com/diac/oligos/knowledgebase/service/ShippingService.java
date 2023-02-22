package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Shipping;

import java.util.List;
import java.util.Optional;

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
     * @return Optional с найденным типом доставки. Пустой Optional, если ничего не найдено
     */
    Optional<Shipping> findById(int id);

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
     * @param shipping Тип доставки, данные которого необходимо обновить
     * @return Обновленный тип доставки
     */
    Shipping update(Shipping shipping);

    /**
     * Удалить тип доставки из системы
     *
     * @param shipping Тип доставки, который необходимо удалить
     */
    void delete(Shipping shipping);
}