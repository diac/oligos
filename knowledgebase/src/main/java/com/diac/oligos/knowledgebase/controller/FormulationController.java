package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Formulation;
import com.diac.oligos.knowledgebase.service.FormulationService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели Formulation
 */
@RestController
@AllArgsConstructor
@RequestMapping("/formulation")
public class FormulationController {

    /**
     * Сервис для работы с объектами модели Formulation
     */
    private final FormulationService formulationService;

    /**
     * Получить все типы препаратов
     *
     * @return Список типов препаратов
     */
    @GetMapping("")
    public ResponseEntity<List<Formulation>> index() {
        return new ResponseEntity<>(
                formulationService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить тип препарата по ID
     *
     * @param id Идентификатор типа препарата
     * @return Ответ с типом препарата и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formulation> getById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(
                    formulationService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получить тип препарата по артикулу
     *
     * @param sku Артикул
     * @return Ответ с типом препарата и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<Formulation> getBySku(@PathVariable("sku") String sku) {
        try {
            return new ResponseEntity<>(
                    formulationService.findBySku(sku),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Добавить новый тип препарата
     *
     * @param formulation Новый тип препарата
     * @return Ответ с созданным типом препарата
     */
    @PostMapping("")
    public ResponseEntity<Formulation> post(@RequestBody @Valid Formulation formulation) {
        return new ResponseEntity<>(
                formulationService.add(formulation),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные типа препарата
     *
     * @param id          Идентификатор типа препарата
     * @param formulation Объект с новыми данными типа препарата
     * @return Ответ с обновленным типом препарата
     */
    @PutMapping("/{id}")
    public ResponseEntity<Formulation> put(@PathVariable("id") int id, @RequestBody @Valid Formulation formulation) {
        return new ResponseEntity<>(
                formulationService.update(id, formulation),
                HttpStatus.OK
        );
    }

    /**
     * Удалить тип препарата
     *
     * @param id Идентификатор типа препарата
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        formulationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}