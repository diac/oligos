package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Scale;
import com.diac.oligos.knowledgebase.repository.ScaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами модели Scale через JPA
 */
@Service
@AllArgsConstructor
public class ScaleJpaService implements ScaleService {

    /**
     * Репозиторий для хранения объектов Scale
     */
    private final ScaleRepository scaleRepository;

    /**
     * Найти все масштабы
     *
     * @return Список масштабов
     */
    @Override
    public List<Scale> findAll() {
        return scaleRepository.findAll();
    }

    /**
     * Найти масштаб по ID
     *
     * @param id Идентификатор масштаба
     * @return Optional с найденным масштабом. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Scale> findById(int id) {
        return scaleRepository.findById(id);
    }

    /**
     * Добавить новый масштаб в систему
     *
     * @param scale Новый масштаб
     * @return Сохраненный масштаб
     */
    @Override
    public Scale add(Scale scale) {
        return scaleRepository.save(scale);
    }

    /**
     * Обновить данные масштаба в системе
     *
     * @param scale Масштаб, данные которого необходимо обновить
     * @return Обновленный масштаб
     */
    @Override
    public Scale update(Scale scale) {
        return scaleRepository.save(scale);
    }

    /**
     * Удалить масштаб из системы
     *
     * @param scale Масштаб, который необходимо удалить
     */
    @Override
    public void delete(Scale scale) {
        scaleRepository.delete(scale);
    }
}