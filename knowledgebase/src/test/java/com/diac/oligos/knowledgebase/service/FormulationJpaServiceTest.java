package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Formulation;
import com.diac.oligos.knowledgebase.config.DataConfig;
import com.diac.oligos.knowledgebase.repository.FormulationRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        FormulationRepository.class,
        FormulationJpaService.class
})
public class FormulationJpaServiceTest {

    @Autowired
    private FormulationRepository formulationRepository;

    private FormulationService formulationService;

    @BeforeEach
    public void init() {
        formulationService = new FormulationJpaService(formulationRepository);
    }

    @Test
    public void whenFindAll() {
        Formulation formulation = formulationService.add(buildFormulation());
        assertThat(formulationService.findAll()).contains(formulation);
    }

    @Test
    public void whenFindById() {
        Formulation formulation = formulationService.add(buildFormulation());
        Formulation formulationInDb = formulationService.findById(formulation.getId()).orElse(new Formulation());
        assertThat(formulationInDb).isEqualTo(formulation);
    }

    @Test
    public void whenFindBySku() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationService.add(buildFormulation(value));
        Formulation formulationInDb = formulationService.findBySku(value).orElse(new Formulation());
        assertThat(formulation).isEqualTo(formulationInDb);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationService.add(buildFormulation(value));
        assertThat(value).isEqualTo(formulation.getName());
        assertThat(value).isEqualTo(formulation.getSku());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThrows(
                ConstraintViolationException.class,
                () -> formulationService.add(buildFormulation(null))
        );
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = buildFormulation(value);
        Formulation duplicateFormulation = buildFormulation(value);
        formulationService.add(formulation);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> formulationService.add(duplicateFormulation)
        );
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationService.add(buildFormulation(value));
        formulation.setName(formulation.getName() + "_updated");
        formulation.setSku(formulation.getSku() + "_updated");
        Formulation updatedFormulation = formulationService.update(formulation);
        assertThat(formulation).isEqualTo(updatedFormulation);
        assertThat(formulation.getName()).isEqualTo(updatedFormulation.getName());
        assertThat(formulation.getSku()).isEqualTo(updatedFormulation.getSku());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationService.add(buildFormulation(value));
        formulation.setName(null);
        formulation.setSku(null);
        assertThrows(
                ConstraintViolationException.class,
                () -> {
                    formulationService.update(formulation);
                    formulationService.findAll();
                }
        );
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationService.add(buildFormulation(value));
        Formulation duplicateFormulation = formulationService.add(buildFormulation(value + "_another"));
        duplicateFormulation.setName(formulation.getName());
        duplicateFormulation.setSku(formulation.getSku());
        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    formulationService.update(duplicateFormulation);
                    formulationService.findAll();
                }
        );
    }

    @Test
    public void whenDelete() {
        Formulation formulation = formulationService.add(buildFormulation());
        formulationService.delete(formulation);
        assertThat(formulationService.findAll()).doesNotContain(formulation);
    }

    private Formulation buildFormulation(String value) {
        return Formulation.builder()
                .name(value)
                .available(true)
                .sku(value)
                .build();
    }

    private Formulation buildFormulation() {
        String value = String.valueOf(System.currentTimeMillis());
        return buildFormulation(value);
    }
}