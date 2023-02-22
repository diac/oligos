package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Purification;
import com.diac.oligos.knowledgebase.config.DataConfig;
import com.diac.oligos.knowledgebase.repository.PurificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        PurificationRepository.class,
        PurificationJpaService.class
})
public class PurificationJpaServiceTest {

    @Autowired
    private PurificationRepository purificationRepository;

    private PurificationService purificationService;

    @BeforeEach
    public void init() {
        purificationService = new PurificationJpaService(purificationRepository);
    }

    @Test
    public void whenFindAll() {
        Purification purification = purificationService.add(buildPurification());
        assertThat(purificationService.findAll()).contains(purification);
    }

    @Test
    public void whenFindById() {
        Purification purification = purificationService.add(buildPurification());
        Purification purificationInDb = purificationService.findById(purification.getId()).orElse(new Purification());
        assertThat(purificationInDb).isEqualTo(purification);
    }

    @Test
    public void whenFindBySku() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationService.add(buildPurification(value));
        Purification purificationInDb = purificationService.findBySku(value).orElse(new Purification());
        assertThat(purification).isEqualTo(purificationInDb);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationService.add(buildPurification(value));
        assertThat(value).isEqualTo(purification.getName());
        assertThat(value).isEqualTo(purification.getSku());
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = purificationService.add(buildPurification(value));
        purification.setName(purification.getName() + "_updated");
        purification.setSku(purification.getSku() + "_updated");
        Purification updatedPurification = purificationService.update(purification);
        assertThat(purification).isEqualTo(updatedPurification);
        assertThat(purification.getName()).isEqualTo(updatedPurification.getName());
        assertThat(purification.getSku()).isEqualTo(updatedPurification.getSku());
    }

    @Test
    public void whenDelete() {
        Purification purification = purificationService.add(buildPurification());
        purificationService.delete(purification);
        assertThat(purificationService.findAll()).doesNotContain(purification);
    }

    private Purification buildPurification(String value) {
        return Purification.builder()
                .name(value)
                .sku(value)
                .build();
    }

    private Purification buildPurification() {
        return buildPurification(String.valueOf(System.currentTimeMillis()));
    }
}