package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Synthesis;
import com.diac.oligos.knowledgebase.service.SynthesisService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели Synthesis
 */
@RestController
@AllArgsConstructor
@RequestMapping("/synthesis")
public class SynthesisController {

    /**
     * Сервис для работы с объектами модели Synthesis
     */
    private final SynthesisService synthesisService;

    /**
     * Получить все синтезы
     *
     * @return Список синтезов
     */
    @GetMapping("")
    public ResponseEntity<List<Synthesis>> index() {
        return new ResponseEntity<>(
                synthesisService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить синтез по ID
     *
     * @param id Идентификатор синтеза
     * @return Ответ с синтезом и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<Synthesis> getById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(
                    synthesisService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получить синтез по артикулу
     *
     * @param sku Артикул
     * @return Ответ с синтезом и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<Synthesis> getBySku(@PathVariable("sku") String sku) {
        try {
            return new ResponseEntity<>(
                    synthesisService.findBySku(sku),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Добавить новый синтез
     *
     * @param synthesis Новый синтез
     * @return Ответ с новым синтезом
     */
    @PostMapping("")
    public ResponseEntity<Synthesis> post(@RequestBody @Valid Synthesis synthesis) {
        return new ResponseEntity<>(
                synthesisService.add(synthesis),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные синтеза
     *
     * @param id        Идентификатор синтеза
     * @param synthesis Объект с новыми данными синтеза
     * @return Ответ с обновленным синтезом
     */
    @PutMapping("/{id}")
    public ResponseEntity<Synthesis> put(@PathVariable("id") int id, @RequestBody @Valid Synthesis synthesis) {
        return new ResponseEntity<>(
                synthesisService.update(id, synthesis),
                HttpStatus.OK
        );
    }

    /**
     * Удалить синтез
     *
     * @param id Идентификатор синтеза
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        synthesisService.delete(id);
        return ResponseEntity.ok().build();
    }
}