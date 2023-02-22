package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Formulation;
import com.diac.oligos.knowledgebase.repository.FormulationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами модели Formulation через JPA
 */
@Service
@AllArgsConstructor
public class FormulationJpaService implements FormulationService {

    /**
     * Репозиторий для хранения объектов Formulation
     */
    private final FormulationRepository formulationRepository;

    /**
     * Найти все типы препаратов
     *
     * @return Список с типами препаратов
     */
    @Override
    public List<Formulation> findAll() {
        return formulationRepository.findAll();
    }

    /**
     * Найти тип препарата по ID
     *
     * @param id Идентификатор типа препарата
     * @return Optional с найденным типом препарата. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Formulation> findById(int id) {
        return formulationRepository.findById(id);
    }

    /**
     * Найти тип препарата по артикулу
     *
     * @param sku Артикул
     * @return Optional с найденным типом препарата. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Formulation> findBySku(String sku) {
        return formulationRepository.findBySku(sku);
    }

    /**
     * Добавить новый тип препарата в систему
     *
     * @param formulation Новый тип препарата
     * @return Сохраненный тип препарата
     */
    @Override
    public Formulation add(Formulation formulation) {
        return formulationRepository.save(formulation);
    }

    /**
     * Обновить данные типа препарата в системе
     *
     * @param formulation Тип препарата, данные которого необходимо обновить
     * @return Обновленный тип препарата
     */
    @Override
    public Formulation update(Formulation formulation) {
        return formulationRepository.save(formulation);
    }

    /**
     * Удалить тип препарата из системы
     *
     * @param formulation Тип препарата, который необходимо удалить
     */
    @Override
    public void delete(Formulation formulation) {
        formulationRepository.delete(formulation);
    }
}