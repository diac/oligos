package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Synthesis;
import com.diac.oligos.knowledgebase.service.SynthesisService;
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
        return synthesisService.findById(id).map(synthesis -> new ResponseEntity<>(
                synthesis,
                HttpStatus.FOUND
        )).orElse(ResponseEntity.notFound().build());
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
     * @param synthesis Объект с новыми данными синтеза
     * @return Ответ с обновленным синтезом
     */
    @PutMapping("")
    public ResponseEntity<Synthesis> put(@RequestBody @Valid Synthesis synthesis) {
        return new ResponseEntity<>(
                synthesisService.update(synthesis),
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
        synthesisService.delete(
                Synthesis.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity.ok().build();
    }
}