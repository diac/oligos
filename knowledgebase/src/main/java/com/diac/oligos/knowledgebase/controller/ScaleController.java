package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Scale;
import com.diac.oligos.knowledgebase.service.ScaleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели Scale
 */
@RestController
@AllArgsConstructor
@RequestMapping("/scale")
public class ScaleController {

    /**
     * Сервис для работы с объектами модели Scale
     */
    private final ScaleService scaleService;

    /**
     * Получить все масштабы
     *
     * @return Список масштабов
     */
    @GetMapping("")
    public ResponseEntity<List<Scale>> index() {
        return new ResponseEntity<>(
                scaleService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить масштаб по ID
     *
     * @param id Идентификатор масштаба
     * @return Ответ с масштабом и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<Scale> getById(@PathVariable("id") int id) {
        return scaleService.findById(id).map(scale -> new ResponseEntity<>(
                scale,
                HttpStatus.FOUND
        )).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Добавить новый масштаб
     *
     * @param scale Новый масштаб
     * @return Ответ с новым масштабом
     */
    @PostMapping("")
    public ResponseEntity<Scale> post(@RequestBody @Valid Scale scale) {
        return new ResponseEntity<>(
                scaleService.add(scale),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные масштаба
     *
     * @param scale Объект с новыми данными масштаба
     * @return Ответ с обновленным масштабом
     */
    @PutMapping("")
    public ResponseEntity<Scale> put(@RequestBody @Valid Scale scale) {
        return new ResponseEntity<>(
                scaleService.update(scale),
                HttpStatus.OK
        );
    }

    /**
     * Удалить масштаб
     *
     * @param id Идентификатор масштаба
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        scaleService.delete(
                Scale.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity.ok().build();
    }
}