package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.SynthesisPrice;
import com.diac.oligos.priceschedule.repository.SynthesisPriceRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели SynthesisPrice через JPA
 */
@Service
@AllArgsConstructor
public class SynthesisPriceJpaService implements SynthesisPriceService {

    private final SynthesisPriceRepository synthesisPriceRepository;

    /**
     * Найти все цены синтезов
     *
     * @return Список с ценами синтезов
     */
    @Override
    public List<SynthesisPrice> findAll() {
        return synthesisPriceRepository.findAll();
    }

    /**
     * Найти цену синтеза по id
     *
     * @param id Идентификатор цены синтеза
     * @return Цена синтеза
     */
    @Override
    public SynthesisPrice findById(int id) {
        return synthesisPriceRepository.findById(id)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Найти цену синтеза по артикулу синтеза
     *
     * @param sku Артикул синтеза
     * @return Цена синтеза
     */
    @Override
    public SynthesisPrice findBySynthesisSku(String sku) {
        return synthesisPriceRepository.findBySynthesisSku(sku)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Добавить новую цену синтеза в систему
     *
     * @param synthesisPrice Новая цена синтеза
     * @return Сохраненная цена синтеза
     */
    @Override
    public SynthesisPrice add(SynthesisPrice synthesisPrice) {
        return synthesisPriceRepository.save(synthesisPrice);
    }

    /**
     * Обновить данные цены синтеза в системе
     *
     * @param id             Идентификатор цены синтеза, данные которой необходимо обновить
     * @param synthesisPrice Объект с обновленными данными цены синтеза
     * @return Обновленная цена синтеза
     */
    @Override
    public SynthesisPrice update(int id, SynthesisPrice synthesisPrice) {
        return synthesisPriceRepository.findById(id)
                .map(synthesisPriceInDb -> {
                    synthesisPrice.setId(id);
                    return synthesisPriceRepository.save(synthesisPrice);
                }).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить цену синтеза из системы
     *
     * @param id Идентификатор цены синтеза, которую необходимо удалить
     */
    @Override
    public void delete(int id) {
        synthesisPriceRepository.findById(id)
                .ifPresentOrElse(
                        synthesisPrice -> synthesisPriceRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}