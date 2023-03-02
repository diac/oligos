package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.FormulationPrice;
import com.diac.oligos.priceschedule.repository.FormulationPriceRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели FormulationPrice через JPA
 */
@Service
@AllArgsConstructor
public class FormulationPriceJpaService implements FormulationPriceService {

    /**
     * Репозиторий для хранения объектов FormulationPrice
     */
    private final FormulationPriceRepository formulationPriceRepository;

    /**
     * Найти все цены типов препаратов
     *
     * @return Список с ценами типов препаратов
     */
    @Override
    public List<FormulationPrice> findAll() {
        return formulationPriceRepository.findAll();
    }

    /**
     * Найти цену типа препарата по ID
     *
     * @param id Идентификатор цены типа препарата
     * @return Цена типа препарата
     * @throws NoResultException Если ничего не найдено
     */
    @Override
    public FormulationPrice findById(int id) {
        return formulationPriceRepository.findById(id)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Найти цену типа препарата по артикулу типа препарата
     *
     * @param sku Артикул типа препарата
     * @return Цена типа препарата
     * @throws NoResultException Если ничего не найдено
     */
    @Override
    public FormulationPrice findByFormulationSku(String sku) {
        return formulationPriceRepository.findByFormulationSku(sku)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Добавить новую цену типа препарата в систему
     *
     * @param formulationPrice Новая цена типа препарата
     * @return Сохраненная цена типа препарата
     */
    @Override
    public FormulationPrice add(FormulationPrice formulationPrice) {
        return formulationPriceRepository.save(formulationPrice);
    }

    /**
     * Обновить данные цены типа препарата в системе
     *
     * @param id               Идентификатор цены типа препарата, данные которой необходимо обновить
     * @param formulationPrice Объект с обновленными данными цены типа препарата
     * @return Обновленная цена типа препарата
     * @throws NoResultException При попытке обновить несуществующую цену типа препарата
     */
    @Override
    public FormulationPrice update(int id, FormulationPrice formulationPrice) {
        return formulationPriceRepository.findById(id)
                .map(formulationPriceInDb -> {
                    formulationPrice.setId(id);
                    return formulationPriceRepository.save(formulationPrice);
                }).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить цену типа препарата из системы
     *
     * @param id Идентификатор цены типа препарата, которую необходимо удалить
     * @throws NoResultException При попытке удалить несуществующую цену типа препарата
     */
    @Override
    public void delete(int id) {
        formulationPriceRepository.findById(id)
                .ifPresentOrElse(
                        formulationPrice -> formulationPriceRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}