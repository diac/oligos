package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.PriceSchedule;

import java.util.List;

/**
 * Сервис для работы с объектами модели PriceSchedule
 */
public interface PriceScheduleService {

    /**
     * Найти все прейскуранты
     *
     * @return Список прейскурантов
     */
    List<PriceSchedule> findAll();

    /**
     * Найти прейскурант по ID
     *
     * @param id Идентификатор прейскуранта
     * @return Прейскурант
     */
    PriceSchedule findById(int id);

    /**
     * Найти действующий прейскурант
     *
     * @return Прейскурант
     */
    PriceSchedule findEffective();

    /**
     * Добавить новый прейскурант в систему
     *
     * @param priceSchedule Новый прейскурант
     * @return Сохраненный прейскурант
     */
    PriceSchedule add(PriceSchedule priceSchedule);

    /**
     * Обновить данные прейскуранта в системе
     *
     * @param id            Идентификатор прейскуранта
     * @param priceSchedule Объект с обновленными данными прейскуранта
     * @return Обновленный прейскурант
     */
    PriceSchedule update(int id, PriceSchedule priceSchedule);

    /**
     * Удалить прейскурант из системы
     *
     * @param id Идентификатор прейскуранта, который необходимо удалить
     */
    void delete(int id);

    /**
     * Сделать прейскурант действительным
     *
     * @param id Идентификатор прейскуранта
     */
    void makeEffective(int id);

    /**
     * Сделать прейскурант недействительным
     *
     * @param id Идентификатор прейскуранта
     */
    void makeIneffective(int id);
}