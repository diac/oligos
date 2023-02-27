package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.enumeration.BaseType;
import com.diac.oligos.domain.model.Synthesis;
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
        SynthesisRepository.class
})
public class SynthesisRepositoryTest {

    @Autowired
    private SynthesisRepository synthesisRepository;

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisRepository.save(
                Synthesis.builder()
                        .name(value)
                        .sku(value)
                        .baseType(BaseType.DNA)
                        .build()
        );
        assertThat(synthesisRepository.findAll()).contains(synthesis);
    }

    @Test
    public void whenFindById() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisRepository.save(
                Synthesis.builder()
                        .name(value)
                        .sku(value)
                        .baseType(BaseType.DNA)
                        .build()
        );
        Synthesis synthesisInDb = synthesisRepository.findById(synthesis.getId()).orElse(new Synthesis());
        assertThat(synthesisInDb).isEqualTo(synthesis);
    }

    @Test
    public void whenFindBySku() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisRepository.save(
                Synthesis.builder()
                        .name(value)
                        .sku(value)
                        .baseType(BaseType.DNA)
                        .build()
        );
        Synthesis synthesisInDb = synthesisRepository.findBySku(synthesis.getSku()).orElse(new Synthesis());
        assertThat(synthesisInDb).isEqualTo(synthesis);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisRepository.save(
                Synthesis.builder()
                        .name(value)
                        .sku(value)
                        .baseType(BaseType.DNA)
                        .build()
        );
        assertThat(value).isEqualTo(synthesis.getName());
        assertThat(value).isEqualTo(synthesis.getSku());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThatThrownBy(
                () -> synthesisRepository.save(
                        Synthesis.builder()
                                .name(null)
                                .sku(null)
                                .baseType(BaseType.DNA)
                                .build()
                )
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = Synthesis.builder()
                .name(value)
                .sku(value)
                .baseType(BaseType.DNA)
                .build();
        Synthesis duplicateSynthesis = Synthesis.builder()
                .name(value)
                .sku(value)
                .baseType(BaseType.DNA)
                .build();
        synthesisRepository.save(synthesis);
        assertThatThrownBy(
                () -> synthesisRepository.save(duplicateSynthesis)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisRepository.save(Synthesis.builder()
                .name(value)
                .sku(value)
                .baseType(BaseType.DNA)
                .build()
        );
        synthesis.setName(synthesis.getName() + "_updated");
        synthesis.setSku(synthesis.getSku() + "_updated");
        Synthesis updatedSynthesis = synthesisRepository.save(synthesis);
        assertThat(synthesis).isEqualTo(updatedSynthesis);
        assertThat(synthesis.getName()).isEqualTo(updatedSynthesis.getName());
        assertThat(synthesis.getSku()).isEqualTo(updatedSynthesis.getSku());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisRepository.save(Synthesis.builder()
                .name(value)
                .sku(value)
                .baseType(BaseType.DNA)
                .build()
        );
        synthesis.setName(null);
        synthesis.setSku(null);
        assertThatThrownBy(
                () -> {
                    synthesisRepository.save(synthesis);
                    synthesisRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisRepository.save(Synthesis.builder()
                .name(value)
                .sku(value)
                .baseType(BaseType.DNA)
                .build()
        );
        Synthesis duplicateSynthesis = synthesisRepository.save(Synthesis.builder()
                .name(value + "_another")
                .sku(value + "_another")
                .baseType(BaseType.DNA)
                .build()
        );
        duplicateSynthesis.setName(synthesis.getName());
        duplicateSynthesis.setSku(synthesis.getSku());
        assertThatThrownBy(
                () -> {
                    synthesisRepository.save(duplicateSynthesis);
                    synthesisRepository.findAll();
                }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisRepository.save(Synthesis.builder()
                .name(value)
                .sku(value)
                .baseType(BaseType.DNA)
                .build()
        );
        synthesisRepository.delete(synthesis);
        assertThat(synthesisRepository.findAll()).doesNotContain(synthesis);
    }
}