package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.ModificationPrice;
import com.diac.oligos.priceschedule.service.ModificationPriceService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели ModificationPrice
 */
@RestController
@AllArgsConstructor
@RequestMapping("/modification_price")
public class ModificationPriceController {

    /**
     * Сервис для работы с объектами модели ModificationPrice
     */
    private final ModificationPriceService modificationPriceService;

    /**
     * Получить все цены модификаторов
     *
     * @return Список цен модификаторов
     */
    @GetMapping("")
    public ResponseEntity<List<ModificationPrice>> index() {
        return new ResponseEntity<>(
                modificationPriceService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить цену модификатора по ID
     *
     * @param id Идентификатор цены модификатора
     * @return Ответ с найденной ценой и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModificationPrice> getById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(
                    modificationPriceService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получить цену модификатора по артикулу
     *
     * @param sku Артикул
     * @return Ответ с найденной ценой и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<ModificationPrice> getBySku(@PathVariable("sku") String sku) {
        try {
            return new ResponseEntity<>(
                    modificationPriceService.findByModificationSku(sku),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Добавить новую цену модификатора
     *
     * @param modificationPrice Новая цена
     * @return Ответ с созданной ценой
     */
    @PostMapping("")
    public ResponseEntity<ModificationPrice> post(@RequestBody @Valid ModificationPrice modificationPrice) {
        return new ResponseEntity<>(
                modificationPriceService.add(modificationPrice),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные цены модификатора
     *
     * @param id                Идентификатор цены
     * @param modificationPrice Объект с новыми данными цены
     * @return Ответ с обновленной ценой
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModificationPrice> put(
            @PathVariable("id") int id,
            @RequestBody @Valid ModificationPrice modificationPrice
    ) {
        return new ResponseEntity<>(
                modificationPriceService.update(id, modificationPrice),
                HttpStatus.OK
        );
    }

    /**
     * Удалить цену модификатора
     *
     * @param id Идентификатор цены модификатора
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        modificationPriceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}