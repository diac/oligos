package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.priceschedule.repository.PriceScheduleRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с объектами модели PriceSchedule
 */
@Service
@AllArgsConstructor
public class PriceScheduleJpaService implements PriceScheduleService {

    /**
     * Репозиторий для хранения объектов PriceSchedule
     */
    private final PriceScheduleRepository priceScheduleRepository;

    /**
     * Найти все прейскуранты
     *
     * @return Список прейскурантов
     */
    @Override
    public List<PriceSchedule> findAll() {
        return priceScheduleRepository.findAll();
    }

    /**
     * Найти прейскурант по ID
     *
     * @param id Идентификатор прейскуранта
     * @return Прейскурант
     * @throws NoResultException Если ничего не найдено
     */
    @Override
    public PriceSchedule findById(int id) {
        return priceScheduleRepository.findById(id)
                .orElseThrow(NoResultException::new);
    }

    /**
     * Найти действующий прейскурант
     *
     * @return Прейскурант
     */
    @Override
    public PriceSchedule findEffective() {
        return priceScheduleRepository.findFirstByEffectiveTrue()
                .orElseThrow(NoResultException::new);
    }

    /**
     * Добавить новый прейскурант в систему
     *
     * @param priceSchedule Новый прейскурант
     * @return Сохраненный прейскурант
     */
    @Override
    public PriceSchedule add(PriceSchedule priceSchedule) {
        return priceScheduleRepository.save(priceSchedule);
    }

    /**
     * Обновить данные прейскуранта в системе
     *
     * @param id            Идентификатор прейскуранта
     * @param priceSchedule Объект с обновленными данными прейскуранта
     * @return Обновленный прейскурант
     * @throws NoResultException При попытке обновить несуществующий прейскурант
     */
    @Override
    public PriceSchedule update(int id, PriceSchedule priceSchedule) {
        return priceScheduleRepository.findById(id)
                .map(priceScheduleInDb -> {
                    priceSchedule.setId(id);
                    return priceScheduleRepository.save(priceSchedule);
                }).orElseThrow(NoResultException::new);
    }

    /**
     * Удалить прейскурант из системы
     *
     * @param id Идентификатор прейскуранта, который необходимо удалить
     */
    @Override
    public void delete(int id) {
        priceScheduleRepository.findById(id)
                .ifPresentOrElse(
                        priceSchedule -> priceScheduleRepository.deleteById(id),
                        () -> {
                            throw new NoResultException();
                        }
                );
    }

    /**
     * Сделать прейскурант действительным
     *
     * @param id Идентификатор прейскуранта
     */
    @Override
    public void makeEffective(int id) {
        priceScheduleRepository.findById(id)
                .ifPresentOrElse(
                        priceSchedule -> {
                            priceSchedule.setEffective(true);
                            priceScheduleRepository.save(priceSchedule);
                        },
                        () -> {
                            throw new NoResultException();
                        }
                );
    }

    /**
     * Сделать прейскурант недействительным
     *
     * @param id Идентификатор прейскуранта
     */
    @Override
    public void makeIneffective(int id) {
        priceScheduleRepository.findById(id)
                .ifPresentOrElse(
                        priceSchedule -> {
                            priceSchedule.setEffective(false);
                            priceScheduleRepository.save(priceSchedule);
                        },
                        () -> {
                            throw new NoResultException();
                        }
                );
    }
}