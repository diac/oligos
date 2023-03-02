package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.ModificationPrice;

import java.util.List;

/**
 * Сервис для работы с объектами модели ModificationPrice
 */
public interface ModificationPriceService {

    /**
     * Найти все цены модификаторов
     *
     * @return Список с ценами модификаторов
     */
    List<ModificationPrice> findAll();

    /**
     * Найти цену модификатора по ID
     *
     * @param id Идентификатор цены модификатора
     * @return Цена модификатора
     */
    ModificationPrice findById(int id);

    /**
     * Найти цену модификатора по артикулу модификатора
     *
     * @param sku Артикул модификатора
     * @return Цена модификатора
     */
    ModificationPrice findByModificationSku(String sku);

    /**
     * Добавить новую цену модификатора в систему
     *
     * @param modificationPrice Новая цена модификатора
     * @return Сохраненная цена модификатора
     */
    ModificationPrice add(ModificationPrice modificationPrice);

    /**
     * Обновить данные цены модификатора в системе
     *
     * @param id                Идентификатор цены модификатора, данные которой необходимо обновить
     * @param modificationPrice Объект с обновленными данными цены модификатора
     * @return Обновленная цена модификатора
     */
    ModificationPrice update(int id, ModificationPrice modificationPrice);

    /**
     * Удалить цену модификатора из системы
     *
     * @param id Идентификатор цены модификатора, которую необходимо удалить
     */
    void delete(int id);
}