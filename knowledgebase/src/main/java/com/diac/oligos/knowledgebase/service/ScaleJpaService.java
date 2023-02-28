package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Scale;
import com.diac.oligos.knowledgebase.repository.ScaleRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return Масштаб
     * @throws NoResultException Если ничего не найдено
     */
    @Override
    public Scale findById(int id) {
        return scaleRepository.findById(id)
                .orElseThrow(NoResultException::new);
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
     * @param id    Идентификатор масштаба, данные которого необходимо обновить
     * @param scale Объект с обновленными данными масштаба
     * @return Обновленный масштаб
     * @throws NoResultException При попытке обновить несуществующий масштаб
     */
    @Override
    public Scale update(int id, Scale scale) {
        return scaleRepository.findById(id)
                .map(scaleInDb -> {
                    scale.setId(id);
                    return scaleRepository.save(scale);
                }).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить масштаб из системы
     *
     * @param id Идентификатор масштаба, который необходимо удалить
     * @throws NoResultException При попытке обновить несуществующий масштаб
     */
    @Override
    public void delete(int id) {
        scaleRepository.findById(id)
                .ifPresentOrElse(
                        scale -> scaleRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}