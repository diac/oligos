package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.FormulationPrice;

import java.util.List;

/**
 * Сервис для работы с объектами модели FormulationPrice
 */
public interface FormulationPriceService {

    /**
     * Найти все цены типов препаратов
     *
     * @return Список с ценами типов препаратов
     */
    List<FormulationPrice> findAll();

    /**
     * Найти цену типа препарата по ID
     *
     * @param id Идентификатор цены типа препарата
     * @return Цена типа препарата
     */
    FormulationPrice findById(int id);

    /**
     * Найти цену типа препарата по артикулу типа препарата
     *
     * @param sku Артикул типа препарата
     * @return Цена типа препарата
     */
    FormulationPrice findByFormulationSku(String sku);

    /**
     * Добавить новую цену типа препарата в систему
     *
     * @param formulationPrice Новая цена типа препарата
     * @return Сохраненная цена типа препарата
     */
    FormulationPrice add(FormulationPrice formulationPrice);

    /**
     * Обновить данные цены типа препарата в системе
     *
     * @param id               Идентификатор цены типа препарата, данные которой необходимо обновить
     * @param formulationPrice Объект с обновленными данными цены типа препарата
     * @return Обновленная цена типа препарата
     */
    FormulationPrice update(int id, FormulationPrice formulationPrice);

    /**
     * Удалить цену типа препарата из системы
     *
     * @param id Идентификатор цены типа препарата, которую необходимо удалить
     */
    void delete(int id);
}