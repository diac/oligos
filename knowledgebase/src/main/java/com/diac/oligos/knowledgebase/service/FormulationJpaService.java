package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Formulation;
import com.diac.oligos.knowledgebase.repository.FormulationRepository;
import jakarta.persistence.NoResultException;
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
     * @param id Идентификатор препарата, данные которого необходимо обновить
     * @param formulation Объект с обновленными данными препарата
     * @return Обновленный тип препарата
     * @throws NoResultException При попытке обновить несуществующий тип препарата
     */
    @Override
    public Formulation update(int id, Formulation formulation) {
        return formulationRepository.findById(id)
                .map(formulationInDb -> {
                    formulation.setId(id);
                    return formulationRepository.save(formulation);
                }).orElseThrow(NoResultException::new);
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