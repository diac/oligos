package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Purification;
import com.diac.oligos.knowledgebase.repository.PurificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами модели Purification через JPA
 */
@Service
@AllArgsConstructor
public class PurificationJpaService implements PurificationService {

    /**
     * Репозиторий для хранения объектов Purification
     */
    private final PurificationRepository purificationRepository;

    /**
     * Найти все типы очистки
     *
     * @return Список типов очистки
     */
    @Override
    public List<Purification> findAll() {
        return purificationRepository.findAll();
    }

    /**
     * Найти тип очистки по ID
     *
     * @param id Идентификатор типа очистки
     * @return Optional с найденным типом очистки. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Purification> findById(int id) {
        return purificationRepository.findById(id);
    }

    /**
     * Найти тип очистки по артикулу
     *
     * @param sku Артикул типа очистки
     * @return Optional с найденным типом очистки. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Purification> findBySku(String sku) {
        return purificationRepository.findBySku(sku);
    }

    /**
     * Добавить новый тип очистки в систему
     *
     * @param purification Новый тип очистки
     * @return Сохраненный тип очистки
     */
    @Override
    public Purification add(Purification purification) {
        return purificationRepository.save(purification);
    }

    /**
     * Обновить данные типа очистки в системе
     *
     * @param purification Тип очистки, данные которого необходимо обновить
     * @return Обновленный тип очистки
     */
    @Override
    public Purification update(Purification purification) {
        return purificationRepository.save(purification);
    }

    /**
     * Удалить тип очистки из системы
     *
     * @param purification Тип очистки, который необходимо удалить
     */
    @Override
    public void delete(Purification purification) {
        purificationRepository.delete(purification);
    }
}