package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.FormulationPrice;
import com.diac.oligos.priceschedule.service.FormulationPriceService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели FormulationPrice
 */
@RestController
@AllArgsConstructor
@RequestMapping("/formulation_price")
public class FormulationPriceController {

    /**
     * Сервис для работы с объектами модели FormulationPrice
     */
    private final FormulationPriceService formulationPriceService;

    /**
     * Получить все цены типов препаратов
     *
     * @return Список цен типов препаратов
     */
    @GetMapping("")
    public ResponseEntity<List<FormulationPrice>> index() {
        return new ResponseEntity<>(
                formulationPriceService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить цену типа препарата по ID
     *
     * @param id Идентификатор цены типа препарата
     * @return Ответ с найденной ценой и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormulationPrice> getById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(
                    formulationPriceService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получить цену типа препарата по артикулу
     *
     * @param sku Артикул
     * @return Ответ с найденной ценой и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<FormulationPrice> getBySku(@PathVariable("sku") String sku) {
        try {
            return new ResponseEntity<>(
                    formulationPriceService.findByFormulationSku(sku),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Добавить новую цену типа препарата
     *
     * @param formulationPrice Новая цена
     * @return Ответ с созданной ценой
     */
    @PostMapping("")
    public ResponseEntity<FormulationPrice> post(@RequestBody @Valid FormulationPrice formulationPrice) {
        return new ResponseEntity<>(
                formulationPriceService.add(formulationPrice),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные цены типа препарата
     *
     * @param id               Идентификатор цены
     * @param formulationPrice Объект с новыми данными цены
     * @return Ответ с обновленной ценой
     */
    @PutMapping("/{id}")
    public ResponseEntity<FormulationPrice> put(
            @PathVariable("id") int id,
            @RequestBody @Valid FormulationPrice formulationPrice
    ) {
        return new ResponseEntity<>(
                formulationPriceService.update(id, formulationPrice),
                HttpStatus.OK
        );
    }

    /**
     * Удалить цену типа препарата
     *
     * @param id Идентификатор цены типа препарата
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        formulationPriceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}