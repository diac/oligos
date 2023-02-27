package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Formulation;
import com.diac.oligos.knowledgebase.config.DataConfig;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        FormulationRepository.class
})
public class FormulationRepositoryTest {

    @Autowired
    private FormulationRepository formulationRepository;

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationRepository.save(
                Formulation.builder()
                        .name(value)
                        .sku(value)
                        .available(true)
                        .build()
        );
        assertThat(formulationRepository.findAll()).contains(formulation);
    }

    @Test
    public void whenFindById() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationRepository.save(
                Formulation.builder()
                        .name(value)
                        .sku(value)
                        .available(true)
                        .build()
        );
        Formulation formulationInDb = formulationRepository.findById(formulation.getId()).orElse(new Formulation());
        assertThat(formulationInDb).isEqualTo(formulation);
    }

    @Test
    public void whenFindBySku() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationRepository.save(
                Formulation.builder()
                        .name(value)
                        .sku(value)
                        .available(true)
                        .build()
        );
        Formulation formulationInDb = formulationRepository.findBySku(formulation.getSku()).orElse(new Formulation());
        assertThat(formulationInDb).isEqualTo(formulation);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationRepository.save(
                Formulation.builder()
                        .name(value)
                        .sku(value)
                        .available(true)
                        .build()
        );
        assertThat(value).isEqualTo(formulation.getName());
        assertThat(value).isEqualTo(formulation.getSku());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThatThrownBy(
                () -> formulationRepository.save(Formulation.builder()
                        .name(null)
                        .sku(null)
                        .build())
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = Formulation.builder()
                .name(value)
                .sku(value)
                .build();
        Formulation duplicateFormulation = Formulation.builder()
                .name(value)
                .sku(value)
                .build();
        formulationRepository.save(formulation);
        assertThatThrownBy(
                () -> formulationRepository.save(duplicateFormulation)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationRepository.save(
                Formulation.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        formulation.setName(formulation.getName() + "_updated");
        formulation.setSku(formulation.getSku() + "_updated");
        Formulation updatedFormulation = formulationRepository.save(formulation);
        assertThat(formulation).isEqualTo(updatedFormulation);
        assertThat(formulation.getName()).isEqualTo(updatedFormulation.getName());
        assertThat(formulation.getSku()).isEqualTo(updatedFormulation.getSku());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationRepository.save(
                Formulation.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        formulation.setName(null);
        formulation.setSku(null);
        assertThatThrownBy(
                () -> {
                    formulationRepository.save(formulation);
                    formulationRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationRepository.save(
                Formulation.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        Formulation duplicateFormulation = formulationRepository.save(
                Formulation.builder()
                        .name(value + "_another")
                        .sku(value + "_another")
                        .build()
        );
        duplicateFormulation.setName(formulation.getName());
        duplicateFormulation.setSku(formulation.getSku());
        assertThatThrownBy(
                () -> {
                    formulationRepository.save(duplicateFormulation);
                    formulationRepository.findAll();
                }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = formulationRepository.save(
                Formulation.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        formulationRepository.delete(formulation);
        assertThat(formulationRepository.findAll()).doesNotContain(formulation);
    }
}