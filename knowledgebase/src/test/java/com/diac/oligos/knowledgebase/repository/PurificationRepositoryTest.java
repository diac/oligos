package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Purification;
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
        PurificationRepository.class
})
public class PurificationRepositoryTest {

    @Autowired
    private PurificationRepository purificationRepository;

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationRepository.save(
                Purification.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        assertThat(purificationRepository.findAll()).contains(purification);
    }

    @Test
    public void whenFindById() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationRepository.save(
                Purification.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        Purification purificationInDb = purificationRepository.findById(purification.getId()).orElse(new Purification());
        assertThat(purificationInDb).isEqualTo(purification);
    }

    @Test
    public void whenFindBySku() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationRepository.save(
                Purification.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        Purification purificationInDb = purificationRepository.findBySku(purification.getSku()).orElse(new Purification());
        assertThat(purificationInDb).isEqualTo(purification);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationRepository.save(
                Purification.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        assertThat(value).isEqualTo(purification.getName());
        assertThat(value).isEqualTo(purification.getSku());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThatThrownBy(
                () -> purificationRepository.save(
                        Purification.builder()
                                .name(null)
                                .sku(null)
                                .build()
                )
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = Purification.builder()
                .name(value)
                .sku(value)
                .build();
        Purification duplicatePurification = Purification.builder()
                .name(value)
                .sku(value)
                .build();
        purificationRepository.save(purification);
        assertThatThrownBy(
                () -> purificationRepository.save(duplicatePurification)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationRepository.save(
                Purification.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        purification.setName(purification.getName() + "_updated");
        purification.setSku(purification.getSku() + "_updated");
        Purification updatedPurification = purificationRepository.save(purification);
        assertThat(purification).isEqualTo(updatedPurification);
        assertThat(purification.getName()).isEqualTo(updatedPurification.getName());
        assertThat(purification.getSku()).isEqualTo(updatedPurification.getSku());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationRepository.save(
                Purification.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        purification.setName(null);
        purification.setSku(null);
        assertThatThrownBy(
                () -> {
                    purificationRepository.save(purification);
                    purificationRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationRepository.save(
                Purification.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        Purification duplicatePurification = purificationRepository.save(
                Purification.builder()
                        .name(value + "_another")
                        .sku(value + "_another")
                        .build()
        );
        duplicatePurification.setName(purification.getName());
        duplicatePurification.setSku(purification.getSku());
        assertThatThrownBy(
                () -> {
                    purificationRepository.save(duplicatePurification);
                    purificationRepository.findAll();
                }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationRepository.save(
                Purification.builder()
                        .name(value)
                        .sku(value)
                        .build()
        );
        purificationRepository.delete(purification);
        assertThat(purificationRepository.findAll()).doesNotContain(purification);
    }
}