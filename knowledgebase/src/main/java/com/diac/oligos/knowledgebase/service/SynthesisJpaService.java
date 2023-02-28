package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Synthesis;
import com.diac.oligos.knowledgebase.repository.SynthesisRepository;
import jakarta.persistence.NoResultException;
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
     * @param id        Идентификатор синтеза, данные которого необходимо обновить
     * @param synthesis Объект с обновленными данными синтеза
     * @return Обновленный синтез
     * @throws NoResultException При попытке обновить несуществующий синтез
     */
    @Override
    public Synthesis update(int id, Synthesis synthesis) {
        return synthesisRepository.findById(id)
                .map(synthesisInDb -> {
                    synthesis.setId(id);
                    return synthesisRepository.save(synthesis);
                }).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить синтез из системы
     *
     * @param id Идентификатор синтеза, который необходимо удалить
     * @throws NoResultException При попытке удалить несуществующий синтез
     */
    @Override
    public void delete(int id) {
        synthesisRepository.findById(id)
                .ifPresentOrElse(
                        synthesis -> synthesisRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}