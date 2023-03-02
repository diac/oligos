package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.PurificationPrice;
import com.diac.oligos.priceschedule.repository.PurificationPriceRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Сервис для работы с объектами модели PurificationPrice через JPA
 */
@Service
@AllArgsConstructor
public class PurificationPriceJpaService implements PurificationPriceService {

    /**
     * Репозиторий для хранения объектов PurificationPrice
     */
    private final PurificationPriceRepository purificationPriceRepository;

    /**
     * Найти все цены типов очистки
     *
     * @return Список с ценами типов очистки
     */
    @Override
    public List<PurificationPrice> findAll() {
        return purificationPriceRepository.findAll();
    }

    /**
     * Найти цену типа очистки по ID
     *
     * @param id Идентификатор цены типа очистки
     * @return Цена типа очистки
     * @throws NoResultException Если ничего не найдено
     */
    @Override
    public PurificationPrice findById(int id) {
        return purificationPriceRepository.findById(id)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Найти цену типа очистки по артикулу типа очистки
     *
     * @param sku Артикул типа очистки
     * @return Цена типа очистки
     * @throws NoResultException Если ничего не найдено
     */
    @Override
    public PurificationPrice findByPurificationSku(String sku) {
        return purificationPriceRepository.findByPurificationSku(sku)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Добавить новую цену типа очистки в систему
     *
     * @param purificationPrice Новая цена типа очистки
     * @return Сохраненная цена типа очистки
     */
    @Override
    public PurificationPrice add(PurificationPrice purificationPrice) {
        return purificationPriceRepository.save(purificationPrice);
    }

    /**
     * Обновить данные цены очистки в системе
     *
     * @param id                Идентификатор цены очистки, данные которой необходимо обновить
     * @param purificationPrice Объект с обновленными данными цены очистки
     * @return Обновленная цена очистки
     */
    @Override
    public PurificationPrice update(int id, PurificationPrice purificationPrice) {
        return purificationPriceRepository.findById(id)
                .map(
                        purificationPriceInDb -> {
                            purificationPrice.setId(id);
                            return purificationPriceRepository.save(purificationPrice);
                        }
                ).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить цену очистки из системы
     *
     * @param id Идентификатор цены очистки, которую необходимо удалить
     */
    @Override
    public void delete(int id) {
        purificationPriceRepository.findById(id)
                .ifPresentOrElse(
                        purificationPrice -> purificationPriceRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}