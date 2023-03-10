package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Modification;
import com.diac.oligos.knowledgebase.service.ModificationService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, реализующий доступ к объектам модели Modification
 */
@RestController
@AllArgsConstructor
@RequestMapping("/modification")
public class ModificationController {

    /**
     * Сервис для работы с объектами модели Modification
     */
    private final ModificationService modificationService;

    /**
     * Получить все модификаторы
     *
     * @return Список модификаторов
     */
    @GetMapping
    public ResponseEntity<List<Modification>> index() {
        return new ResponseEntity<>(
                modificationService.findAll(),
                HttpStatus.OK
        );
    }

    /**
     * Получить модификатор по ID
     *
     * @param id Идентификатор модификатора
     * @return Ответ с модификатором и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<Modification> getById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(
                    modificationService.findById(id),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получить модификатор по артикулу
     *
     * @param sku Артикул
     * @return Ответ с модификатором и статусом Found. Пустой ответ со статусом Not Found, если ничего не найдено
     */
    @GetMapping("/find_by_sku/{sku}")
    public ResponseEntity<Modification> getBySku(@PathVariable("sku") String sku) {
        try {
            return new ResponseEntity<>(
                    modificationService.findBySku(sku),
                    HttpStatus.FOUND
            );
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Добавить новый модификатор
     *
     * @param modification Новый модификатор
     * @return Ответ с созданным модификатором
     */
    @PostMapping("")
    public ResponseEntity<Modification> post(@RequestBody @Valid Modification modification) {
        return new ResponseEntity<>(
                modificationService.add(modification),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить данные модификатора
     *
     * @param id           Идентификатор модификатора
     * @param modification Объект с новыми данными модификатора
     * @return Ответ с обновленным модификатором
     */
    @PutMapping("/{id}")
    public ResponseEntity<Modification> put(@PathVariable("id") int id, @RequestBody @Valid Modification modification) {
        return new ResponseEntity<>(
                modificationService.update(id, modification),
                HttpStatus.OK
        );
    }

    /**
     * Удалить модификатор
     *
     * @param id Идентификатор модификатора
     * @return Тело ответа со статусом
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        modificationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}