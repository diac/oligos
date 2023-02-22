package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Modification;
import com.diac.oligos.knowledgebase.repository.ModificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ModificationJpaServiceTest {

    @Autowired
    private ModificationRepository modificationRepository;

    private ModificationService modificationService;

    @BeforeEach
    public void init() {
        modificationService = new ModificationJpaService(modificationRepository);
    }

    @Test
    public void whenFindAll() {
        Modification modification = modificationService.add(buildModification());
        assertThat(modificationService.findAll()).contains(modification);
    }

    @Test
    public void whenFindById() {
        Modification modification = modificationService.add(buildModification());
        Modification modificationInDb = modificationService.findById(modification.getId()).orElse(new Modification());
        assertThat(modificationInDb).isEqualTo(modification);
    }

    @Test
    public void whenFindBySku() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationService.add(buildModification(value));
        Modification modificationInDb = modificationService.findBySku(value).orElse(new Modification());
        assertThat(modification).isEqualTo(modificationInDb);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationService.add(buildModification(value));
        assertThat(value).isEqualTo(modification.getName());
        assertThat(value).isEqualTo(modification.getCode());
        assertThat(value).isEqualTo(modification.getSku());
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = modificationService.add(buildModification(value));
        modification.setName(modification.getName() + "_updated");
        modification.setCode(modification.getCode() + "_updated");
        modification.setSku(modification.getSku() + "_updated");
        Modification updatedModification = modificationService.update(modification);
        assertThat(modification).isEqualTo(updatedModification);
        assertThat(modification.getName()).isEqualTo(updatedModification.getName());
        assertThat(modification.getCode()).isEqualTo(updatedModification.getCode());
        assertThat(modification.getSku()).isEqualTo(updatedModification.getSku());
    }

    @Test
    public void whenDelete() {
        Modification modification = modificationService.add(buildModification());
        modificationService.delete(modification);
        assertThat(modificationService.findAll()).doesNotContain(modification);
    }

    private Modification buildModification(String value) {
        return Modification.builder()
                .name(value)
                .code(value)
                .sku(value)
                .build();
    }

    private Modification buildModification() {
        return buildModification(String.valueOf(System.currentTimeMillis()));
    }
}