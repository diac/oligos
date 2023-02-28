package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Modification;
import com.diac.oligos.knowledgebase.repository.ModificationRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами модели Modification через JPA
 */
@Service
@AllArgsConstructor
public class ModificationJpaService implements ModificationService {

    /**
     * Репозиторий для хранения объектов Modification
     */
    private final ModificationRepository modificationRepository;

    /**
     * Найти все модификаторы
     *
     * @return Список модификаторов
     */
    @Override
    public List<Modification> findAll() {
        return modificationRepository.findAll();
    }

    /**
     * Найти модификатор по ID
     *
     * @param id Идентификатор модификатора
     * @return Optional с найденным модификатором. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Modification> findById(int id) {
        return modificationRepository.findById(id);
    }

    /**
     * Найти модификатор по артикулу
     *
     * @param sku Артикул модификатора
     * @return Optional с найденным модификатором. Пустой Optional, если ничего не найдено
     */
    @Override
    public Optional<Modification> findBySku(String sku) {
        return modificationRepository.findBySku(sku);
    }

    /**
     * Добавить новый модификатор в систему
     *
     * @param modification Новый модификатор
     * @return Сохраненный модификатор
     */
    @Override
    public Modification add(Modification modification) {
        return modificationRepository.save(modification);
    }

    /**
     * Обновить данные модификатора в системе
     *
     * @param id           Идентификатор модификатора, данные которого необходимо обновить
     * @param modification Объект с обновленными данными модификатора
     * @return Обновленный модификатор
     * @throws NoResultException При попытке обновить несуществующий модификатор
     */
    @Override
    public Modification update(int id, Modification modification) {
        return modificationRepository.findById(id)
                .map(modificationInDb -> {
                    modification.setId(id);
                    return modificationRepository.save(modification);
                }).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить модификатор из системы
     *
     * @param id Идентификатор модификатора, который необходимо удалить
     * @throws NoResultException При попытке удалить несуществующий модификатор
     */
    @Override
    public void delete(int id) {
        modificationRepository.findById(id)
                .ifPresentOrElse(
                        modification -> modificationRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}