package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.ModificationPrice;
import com.diac.oligos.priceschedule.repository.ModificationPriceRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели ModificationPrice через JPA
 */
@Service
@AllArgsConstructor
public class ModificationPriceJpaService implements ModificationPriceService {

    /**
     * Репозиторий для хранения объектов ModificationPrice
     */
    private final ModificationPriceRepository modificationPriceRepository;

    /**
     * Найти все цены модификаторов
     *
     * @return Список с ценами модификаторов
     */
    @Override
    public List<ModificationPrice> findAll() {
        return modificationPriceRepository.findAll();
    }

    /**
     * Найти цену модификатора по ID
     *
     * @param id Идентификатор цены модификатора
     * @return Цена модификатора
     * @throws NoResultException Если ничего не найдено
     */
    @Override
    public ModificationPrice findById(int id) {
        return modificationPriceRepository.findById(id)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Найти цену модификатора по артикулу модификатора
     *
     * @param sku Артикул модификатора
     * @return Цена модификатора
     */
    @Override
    public ModificationPrice findByModificationSku(String sku) {
        return modificationPriceRepository.findByModificationSku(sku)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Добавить новую цену модификатора в систему
     *
     * @param modificationPrice Новая цена модификатора
     * @return Сохраненная цена модификатора
     */
    @Override
    public ModificationPrice add(ModificationPrice modificationPrice) {
        return modificationPriceRepository.save(modificationPrice);
    }

    /**
     * Обновить данные цены модификатора в системе
     *
     * @param id                Идентификатор цены модификатора, данные которой необходимо обновить
     * @param modificationPrice Объект с обновленными данными цены модификатора
     * @return Обновленная цена модификатора
     * @throws NoResultException При попытке обновить несуществующую цену модификатора
     */
    @Override
    public ModificationPrice update(int id, ModificationPrice modificationPrice) {
        return modificationPriceRepository.findById(id)
                .map(modificationPriceInDb -> {
                    modificationPrice.setId(id);
                    return modificationPriceRepository.save(modificationPrice);
                }).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить цену модификатора из системы
     *
     * @param id Идентификатор цены модификатора, которую необходимо удалить
     * @throws NoResultException При попытке обновить несуществующую цену модификатора@throws NoResultException При попытке обновить несуществующую цену модификатора
     */
    @Override
    public void delete(int id) {
        modificationPriceRepository.findById(id)
                .ifPresentOrElse(
                        modificationPrice -> modificationPriceRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}