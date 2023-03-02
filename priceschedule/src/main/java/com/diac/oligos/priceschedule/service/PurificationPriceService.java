package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.PurificationPrice;

import java.util.List;

/**
 * Сервис для работы с объектами модели PurificationPrice
 */
public interface PurificationPriceService {

    /**
     * Найти все цены типов очистки
     *
     * @return Список с ценами типов очистки
     */
    List<PurificationPrice> findAll();

    /**
     * Найти цену типа очистки по ID
     *
     * @param id Идентификатор цены типа очистки
     * @return Цена типа очистки
     */
    PurificationPrice findById(int id);

    /**
     * Найти цену типа очистки по артикулу типа очистки
     *
     * @param sku Артикул типа очистки
     * @return Цена типа очистки
     */
    PurificationPrice findByPurificationSku(String sku);

    /**
     * Добавить новую цену типа очистки в систему
     *
     * @param purificationPrice Новая цена типа очистки
     * @return Сохраненная цена типа очистки
     */
    PurificationPrice add(PurificationPrice purificationPrice);

    /**
     * Обновить данные цены очистки в системе
     *
     * @param id                Идентификатор цены очистки, данные которой необходимо обновить
     * @param purificationPrice Объект с обновленными данными цены очистки
     * @return Обновленная цена очистки
     */
    PurificationPrice update(int id, PurificationPrice purificationPrice);

    /**
     * Удалить цену очистки из системы
     *
     * @param id Идентификатор цены очистки, которую необходимо удалить
     */
    void delete(int id);
}