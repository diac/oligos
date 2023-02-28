package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Formulation;
import com.diac.oligos.knowledgebase.repository.FormulationRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return Тип препарата
     */
    @Override
    public Formulation findById(int id) {

        return formulationRepository.findById(id)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Найти тип препарата по артикулу
     *
     * @param sku Артикул
     * @return Тип препарата
     */
    @Override
    public Formulation findBySku(String sku) {
        return formulationRepository.findBySku(sku)
                .orElseThrow(NoResultException::new);
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
     * @param id          Идентификатор препарата, данные которого необходимо обновить
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
     * @param id Идентификатор препарата, который необходимо удалить
     * @throws NoResultException При попытке удалить несуществующий тип препарата
     */
    @Override
    public void delete(int id) {
        formulationRepository.findById(id)
                .ifPresentOrElse(
                        formulation -> formulationRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}