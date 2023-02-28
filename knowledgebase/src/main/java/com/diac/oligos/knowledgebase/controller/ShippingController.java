package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Shipping;
import com.diac.oligos.knowledgebase.service.ShippingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели Shipping
 */
@RestController
@AllArgsConstructor
@RequestMapping("/shipping")
public class ShippingController {

    /**
     * Сервис для работы с объектами модели Shipping
     */
    private final ShippingService shippingService;

    /**
     * Получить все типы доставки
     *
     * @return Список типов доставки
     */
    @GetMapping()
    public ResponseEntity<List<Shipping>> index() {
        return new ResponseEntity<>(
                shippingService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить тип доставки по ID
     *
     * @param id Идентификатор типа доставки
     * @return Ответ с типом доставки и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getById(@PathVariable("id") int id) {
        return shippingService.findById(id).map(shipping -> new ResponseEntity<>(
                shipping,
                HttpStatus.FOUND
        )).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Добавить новый тип доставки
     *
     * @param shipping Новый тип доставки
     * @return Ответ с новым типом доставки
     */
    @PostMapping("")
    public ResponseEntity<Shipping> post(@RequestBody @Valid Shipping shipping) {
        return new ResponseEntity<>(
                shippingService.add(shipping),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные типа доставки
     *
     * @param id       Идентификатор типа доставки
     * @param shipping Объект с новыми данными типа доставки
     * @return Ответ с обновленным типом доставки
     */
    @PutMapping("/{id}")
    public ResponseEntity<Shipping> put(@PathVariable("id") int id, @RequestBody @Valid Shipping shipping) {
        return new ResponseEntity<>(
                shippingService.update(id, shipping),
                HttpStatus.OK
        );
    }

    /**
     * Удалить тип доставки
     *
     * @param id Идентификатор типа доставки
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        shippingService.delete(
                Shipping.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity.ok().build();
    }
}