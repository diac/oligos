package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.SynthesisPrice;
import com.diac.oligos.priceschedule.service.SynthesisPriceService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели SynthesisPrice
 */
@RestController
@AllArgsConstructor
@RequestMapping("/synthesis_price")
public class SynthesisPriceController {

    /**
     * Сервис для работы с объектами модели SynthesisPrice
     */
    private final SynthesisPriceService synthesisPriceService;

    /**
     * Получить все цены синтезов
     *
     * @return Список цен синтезов
     */
    @GetMapping("")
    public ResponseEntity<List<SynthesisPrice>> index() {
        return new ResponseEntity<>(
                synthesisPriceService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить цену синтеза по ID
     *
     * @param id Идентификатор цены
     * @return Ответ с найденной ценой и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<SynthesisPrice> getById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(
                    synthesisPriceService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получить цену синтеза по артикулу
     *
     * @param sku Артикул
     * @return Ответ с найденной ценой и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<SynthesisPrice> getBySku(@PathVariable("sku") String sku) {
        try {
            return new ResponseEntity<>(
                    synthesisPriceService.findBySynthesisSku(sku),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Добавить новую цену синтеза
     *
     * @param synthesisPrice Новая цена
     * @return Ответ с созданной ценой
     */
    @PostMapping("")
    public ResponseEntity<SynthesisPrice> post(@RequestBody @Valid SynthesisPrice synthesisPrice) {
        return new ResponseEntity<>(
                synthesisPriceService.add(synthesisPrice),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные цены синтеза
     *
     * @param id             Идентификатор цены
     * @param synthesisPrice Объект с новыми данными цены
     * @return Ответ с обновленной ценой
     */
    @PutMapping("/{id}")
    public ResponseEntity<SynthesisPrice> put(
            @PathVariable("id") int id,
            @RequestBody @Valid SynthesisPrice synthesisPrice
    ) {
        return new ResponseEntity<>(
                synthesisPriceService.update(id, synthesisPrice),
                HttpStatus.OK
        );
    }

    /**
     * Удалить цену синтеза
     *
     * @param id Идентификатор цены
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        synthesisPriceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}