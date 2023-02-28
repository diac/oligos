package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Purification;
import com.diac.oligos.knowledgebase.service.PurificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели Purification
 */
@RestController
@AllArgsConstructor
@RequestMapping("/purification")
public class PurificationController {

    /**
     * Сервис для работы с объектами модели Purification
     */
    private final PurificationService purificationService;

    /**
     * Получить все типы очистки
     *
     * @return Список типов очистки
     */
    @GetMapping("")
    public ResponseEntity<List<Purification>> index() {
        return new ResponseEntity<>(
                purificationService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить тип очистки по ID
     *
     * @param id Идентификатор типа очистки
     * @return Ответ с типом очистки и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<Purification> getById(@PathVariable("id") int id) {
        return purificationService.findById(id).map(purification -> new ResponseEntity<>(
                purification,
                HttpStatus.FOUND
        )).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Получить тип очистки по артикулу
     *
     * @param sku Артикул
     * @return Ответ с типом очистки и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<Purification> getBySku(@PathVariable("sku") String sku) {
        return purificationService.findBySku(sku).map(purification -> new ResponseEntity<>(
                purification,
                HttpStatus.FOUND
        )).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Добавить новы тип очистки
     *
     * @param purification Новый тип очистки
     * @return Ответ с созданным типом очистки
     */
    @PostMapping("")
    public ResponseEntity<Purification> post(@RequestBody @Valid Purification purification) {
        return new ResponseEntity<>(
                purificationService.add(purification),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные типа очистки
     *
     * @param id           Идентификатор типа очистки
     * @param purification Объект с новыми данными типа очистки
     * @return Ответ с обновленным типом очистки
     */
    @PutMapping("/{id}")
    public ResponseEntity<Purification> put(@PathVariable("id") int id, @RequestBody @Valid Purification purification) {
        return new ResponseEntity<>(
                purificationService.update(id, purification),
                HttpStatus.OK
        );
    }

    /**
     * Удалить тип очистки
     *
     * @param id Идентификатор типа очистки
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        purificationService.delete(id);
        return ResponseEntity.ok().build();
    }
}