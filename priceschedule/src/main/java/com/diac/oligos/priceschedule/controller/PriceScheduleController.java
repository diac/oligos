package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.priceschedule.service.PriceScheduleService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели PriceSchedule
 */
@RestController
@AllArgsConstructor
@RequestMapping("/price_schedule")
public class PriceScheduleController {

    /**
     * Сервис для работы с объектами модели PriceSchedule
     */
    private final PriceScheduleService priceScheduleService;

    /**
     * Получить все прейскуранты
     *
     * @return Список прейскурантов
     */
    @GetMapping("")
    public ResponseEntity<List<PriceSchedule>> index() {
        return new ResponseEntity<>(
                priceScheduleService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить прейскурант по ID
     *
     * @param id Идентификатор прейскуранта
     * @return Ответ с найденным прейскурантом. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriceSchedule> getById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(
                    priceScheduleService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получить действующий прейскурант
     *
     * @return Ответ с действующим прейскурантом.
     * Пустой ответ со статусом Not Found, если в системе нет действующего прейскуранта
     */
    @GetMapping("/effective")
    public ResponseEntity<PriceSchedule> getEffective() {
        try {
            return new ResponseEntity<>(
                    priceScheduleService.findEffective(),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Добавить новый прейскурант
     *
     * @param priceSchedule Новый прейскурант
     * @return Ответ с созданным прейскурантом
     */
    @PostMapping("")
    public ResponseEntity<PriceSchedule> post(@RequestBody @Valid PriceSchedule priceSchedule) {
        return new ResponseEntity<>(
                priceScheduleService.add(priceSchedule),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные прейскуранта
     *
     * @param id            Идентификатор прейскуранта
     * @param priceSchedule Объект с новыми данными прейскуранта
     * @return Ответ с обновленным прейскурантом
     */
    @PutMapping("/{id}")
    public ResponseEntity<PriceSchedule> put(
            @PathVariable("id") int id,
            @RequestBody @Valid PriceSchedule priceSchedule
    ) {
        return new ResponseEntity<>(
                priceScheduleService.update(id, priceSchedule),
                HttpStatus.OK
        );
    }

    /**
     * Удалить прейскурант
     *
     * @param id Идентификатор прейскуранта
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        priceScheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Сделать прейскурант действительным
     *
     * @param id Идентификатор прейскуранта
     * @return Тело ответа со статусом
     */
    @PatchMapping("/{id}/make_effective")
    public ResponseEntity<Void> makeEffective(@PathVariable("id") int id) {
        priceScheduleService.makeEffective(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Сделать прейскурант недействительным
     *
     * @param id Идентификатор прейскуранта
     * @return Тело ответа со статусом
     */
    @PatchMapping("/{id}/make_ineffective")
    public ResponseEntity<Void> makeIneffective(@PathVariable("id") int id) {
        priceScheduleService.makeIneffective(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}