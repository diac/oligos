package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Modification;
import com.diac.oligos.knowledgebase.config.DataConfig;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        ModificationRepository.class
})
public class ModificationRepositoryTest {

    @Autowired
    private ModificationRepository modificationRepository;

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationRepository.save(
                Modification.builder()
                        .name(value)
                        .sku(value)
                        .code(value)
                        .build()
        );
        assertThat(modificationRepository.findAll()).contains(modification);
    }

    @Test
    public void whenFindById() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationRepository.save(
                Modification.builder()
                        .name(value)
                        .sku(value)
                        .code(value)
                        .build()
        );
        Modification modificationInDb = modificationRepository.findById(modification.getId()).orElse(new Modification());
        assertThat(modificationInDb).isEqualTo(modification);
    }

    @Test
    public void whenFindBySku() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationRepository.save(
                Modification.builder()
                        .name(value)
                        .sku(value)
                        .code(value)
                        .build()
        );
        Modification modificationInDb = modificationRepository.findBySku(modification.getSku()).orElse(new Modification());
        assertThat(modificationInDb).isEqualTo(modification);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationRepository.save(
                Modification.builder()
                        .name(value)
                        .sku(value)
                        .code(value)
                        .build()
        );
        assertThat(value).isEqualTo(modification.getName());
        assertThat(value).isEqualTo(modification.getCode());
        assertThat(value).isEqualTo(modification.getSku());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThatThrownBy(
                () -> modificationRepository.save(
                        Modification.builder()
                                .name(null)
                                .sku(null)
                                .code(null)
                                .build()
                )
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = Modification.builder()
                .name(value)
                .sku(value)
                .code(value)
                .build();
        Modification duplicateModification = Modification.builder()
                .name(value)
                .sku(value)
                .code(value)
                .build();
        modificationRepository.save(modification);
        assertThatThrownBy(
                () -> modificationRepository.save(duplicateModification)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationRepository.save(
                Modification.builder()
                        .name(value)
                        .sku(value)
                        .code(value)
                        .build()
        );
        modification.setName(modification.getName() + "_updated");
        modification.setCode(modification.getCode() + "_updated");
        modification.setSku(modification.getSku() + "_updated");
        Modification updatedModification = modificationRepository.save(modification);
        assertThat(modification).isEqualTo(updatedModification);
        assertThat(modification.getName()).isEqualTo(updatedModification.getName());
        assertThat(modification.getCode()).isEqualTo(updatedModification.getCode());
        assertThat(modification.getSku()).isEqualTo(updatedModification.getSku());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationRepository.save(
                Modification.builder()
                        .name(value)
                        .sku(value)
                        .code(value)
                        .build()
        );
        modification.setName(null);
        modification.setSku(null);
        modification.setSku(null);
        assertThatThrownBy(
                () -> {
                    modificationRepository.save(modification);
                    modificationRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationRepository.save(
                Modification.builder()
                        .name(value)
                        .sku(value)
                        .code(value)
                        .build()
        );
        Modification duplicateModification = modificationRepository.save(
                Modification.builder()
                        .name(value + "_another")
                        .sku(value + "_another")
                        .code(value + "_another")
                        .build()
        );
        duplicateModification.setName(modification.getName());
        duplicateModification.setSku(modification.getSku());
        duplicateModification.setCode(modification.getCode());
        assertThatThrownBy(
                () -> {
                    modificationRepository.save(duplicateModification);
                    modificationRepository.findAll();
                }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationRepository.save(
                Modification.builder()
                        .name(value)
                        .sku(value)
                        .code(value)
                        .build()
        );
        modificationRepository.delete(modification);
        assertThat(modificationRepository.findAll()).doesNotContain(modification);
    }
}