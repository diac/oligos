package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Shipping;
import com.diac.oligos.knowledgebase.repository.ShippingRepository;
import jakarta.persistence.NoResultException;
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
     * @param id       Идентификатор типа доставки, данные которого необходимо обновить
     * @param shipping Объект с обновленными данными типа доставки
     * @return Обновленный тип доставки
     * @throws NoResultException При попытке обновить несуществующий тип доставки
     */
    @Override
    public Shipping update(int id, Shipping shipping) {
        return shippingRepository.findById(id)
                .map(shippingInDb -> {
                    shipping.setId(id);
                    return shippingRepository.save(shipping);
                }).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить тип доставки из системы
     *
     * @param id Идентификатор типа доставки, который необходимо удалить
     * @throws NoResultException При попытке обновить несуществующий тип доставки
     */
    @Override
    public void delete(int id) {
        shippingRepository.findById(id)
                .ifPresentOrElse(
                        shipping -> shippingRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}