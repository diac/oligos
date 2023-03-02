package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.SynthesisPrice;

import java.util.List;

/**
 * Сервис для работы с объектами модели SynthesisPrice
 */
public interface SynthesisPriceService {

    /**
     * Найти все цены синтезов
     *
     * @return Список с ценами синтезов
     */
    List<SynthesisPrice> findAll();

    /**
     * Найти цену синтеза по id
     *
     * @param id Идентификатор цены синтеза
     * @return Цена синтеза
     */
    SynthesisPrice findById(int id);

    /**
     * Найти цену синтеза по артикулу синтеза
     *
     * @param sku Артикул синтеза
     * @return Цена синтеза
     */
    SynthesisPrice findBySynthesisSku(String sku);

    /**
     * Добавить новую цену синтеза в систему
     *
     * @param synthesisPrice Новая цена синтеза
     * @return Сохраненная цена синтеза
     */
    SynthesisPrice add(SynthesisPrice synthesisPrice);

    /**
     * Обновить данные цены синтеза в системе
     *
     * @param id             Идентификатор цены синтеза, данные которой необходимо обновить
     * @param synthesisPrice Объект с обновленными данными цены синтеза
     * @return Обновленная цена синтеза
     */
    SynthesisPrice update(int id, SynthesisPrice synthesisPrice);

    /**
     * Удалить цену синтеза из системы
     *
     * @param id Идентификатор цены синтеза, которую необходимо удалить
     */
    void delete(int id);
}