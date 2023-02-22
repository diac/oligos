package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Shipping;
import com.diac.oligos.knowledgebase.repository.ShippingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами модели Shipping
 */
@Service
@AllArgsConstructor
public class ShippingJpaService implements ShippingService {

    /**
     * Репозиторий для хранения объектов Shipping
     */
    private final ShippingRepository shippingRepository;

    /**
     * Найти все типы доставки
     *
     * @return Список типов доставки
     */
    @Override
    public List<Shipping> findAll() {
        return shippingRepository.findAll();
    }

    /**
     * Найти тип доставки по ID
     *
     * @param id Идентификатор типа доставки
     * @return Optional с найденным типом доставки. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Shipping> findById(int id) {
        return shippingRepository.findById(id);
    }

    /**
     * Добавить новый тип доставки в систему
     *
     * @param shipping Новый тип доставки
     * @return Сохраненный тип доставки
     */
    @Override
    public Shipping add(Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    /**
     * Обновить данные типа доставки в системе
     *
     * @param shipping Тип доставки, данные которого необходимо обновить
     * @return Обновленный тип доставки
     */
    @Override
    public Shipping update(Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    /**
     * Удалить тип доставки из системы
     *
     * @param shipping Тип доставки, который необходимо удалить
     */
    @Override
    public void delete(Shipping shipping) {
        shippingRepository.delete(shipping);
    }
}