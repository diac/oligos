package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Synthesis;
import com.diac.oligos.knowledgebase.repository.SynthesisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами модели Synthesis через JPA
 */
@Service
@AllArgsConstructor
public class SynthesisJpaService implements SynthesisService {

    /**
     * Репозиторий для хранения объектов Synthesis
     */
    private final SynthesisRepository synthesisRepository;

    /**
     * Найти все синтезы
     *
     * @return Список синтезов
     */
    @Override
    public List<Synthesis> findAll() {
        return synthesisRepository.findAll();
    }

    /**
     * Найти синтез по ID
     *
     * @param id Идентификатор синтеза
     * @return Optional с найденным синтезом. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Synthesis> findById(int id) {
        return synthesisRepository.findById(id);
    }

    /**
     * Найти синтез по артикулу
     *
     * @param sku Артикул
     * @return Optional с найденным синтезом. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Synthesis> findBySku(String sku) {
        return synthesisRepository.findBySku(sku);
    }

    /**
     * Добавить новый синтез в систему
     *
     * @param synthesis Новый синтез
     * @return Сохраненный синтез
     */
    @Override
    public Synthesis add(Synthesis synthesis) {
        return synthesisRepository.save(synthesis);
    }

    /**
     * Обновить данные синтеза в системе
     *
     * @param synthesis Синтез, данные которого необходимо обновить
     * @return Обновленный синтез
     */
    @Override
    public Synthesis update(Synthesis synthesis) {
        return synthesisRepository.save(synthesis);
    }

    /**
     * Удалить синтез из системы
     *
     * @param synthesis Синтез, который необходимо удалить
     */
    @Override
    public void delete(Synthesis synthesis) {
        synthesisRepository.delete(synthesis);
    }
}