package com.diac.oligos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Модель данных "Прейскурант"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PriceSchedule {

    /**
     * Идентификатор прейскуранта
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя прейскуранта
     */
    private String name;

    /**
     * Дата создания прейскуранта
     */
    private LocalDateTime created;

    /**
     * Признак действительности прейскуранта
     */
    private boolean effective;
}