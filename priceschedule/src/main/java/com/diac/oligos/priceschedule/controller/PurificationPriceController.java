package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.PurificationPrice;
import com.diac.oligos.priceschedule.service.PurificationPriceService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели PurificationPrice
 */
@RestController
@AllArgsConstructor
@RequestMapping("/purification_price")
public class PurificationPriceController {

    /**
     * Сервис для работы с объектами модели PurificationPrice
     */
    private final PurificationPriceService purificationPriceService;

    /**
     * Получить все цены типов очистки
     *
     * @return Список цен типов очистки
     */
    @GetMapping("")
    public ResponseEntity<List<PurificationPrice>> index() {
        return new ResponseEntity<>(
                purificationPriceService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить цену типа очистки по ID
     *
     * @param id Идентификатор цены типа очистки
     * @return Ответ с найденной ценой и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<PurificationPrice> getById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(
                    purificationPriceService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получить цену типа очистки по артикулу
     *
     * @param sku Артикул
     * @return Ответ с найденной ценой и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<PurificationPrice> getBySku(@PathVariable("sku") String sku) {
        try {
            return new ResponseEntity<>(
                    purificationPriceService.findByPurificationSku(sku),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Добавить новую цену типа очистки
     *
     * @param purificationPrice Новая цена
     * @return Ответ с созданной ценой
     */
    @PostMapping("")
    public ResponseEntity<PurificationPrice> post(@RequestBody @Valid PurificationPrice purificationPrice) {
        return new ResponseEntity<>(
                purificationPriceService.add(purificationPrice),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные цены типа очистки
     *
     * @param id                Идентификатор цены
     * @param purificationPrice Объект с новыми данными цены
     * @return Ответ с обновленной ценой
     */
    @PutMapping("/{id}")
    public ResponseEntity<PurificationPrice> put(
            @PathVariable("id") int id,
            @RequestBody @Valid PurificationPrice purificationPrice
    ) {
        return new ResponseEntity<>(
                purificationPriceService.update(id, purificationPrice),
                HttpStatus.OK
        );
    }

    /**
     * Удалить цену типа очистки
     *
     * @param id Идентификатор цены
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        purificationPriceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}