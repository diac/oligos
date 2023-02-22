package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.enumeration.BaseType;
import com.diac.oligos.domain.model.Synthesis;
import com.diac.oligos.knowledgebase.config.DataConfig;
import com.diac.oligos.knowledgebase.repository.SynthesisRepository;
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
        SynthesisRepository.class,
        SynthesisJpaService.class
})
public class SynthesisJpaServiceTest {

    @Autowired
    private SynthesisRepository synthesisRepository;

    private SynthesisService synthesisService;

    @BeforeEach
    public void init() {
        synthesisService = new SynthesisJpaService(synthesisRepository);
    }

    @Test
    public void whenFindAll() {
        Synthesis synthesis = synthesisService.add(buildSynthesis());
        assertThat(synthesisService.findAll()).contains(synthesis);
    }

    @Test
    public void whenFindById() {
        Synthesis synthesis = synthesisService.add(buildSynthesis());
        Synthesis synthesisInDb = synthesisService.findById(synthesis.getId()).orElse(new Synthesis());
        assertThat(synthesisInDb).isEqualTo(synthesis);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisService.add(buildSynthesis(value, BaseType.DNA));
        assertThat(value).isEqualTo(synthesis.getName());
        assertThat(value).isEqualTo(synthesis.getSku());
        assertThat(BaseType.DNA).isEqualTo(synthesis.getBaseType());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThrows(
                DataIntegrityViolationException.class,
                () -> synthesisService.add(buildSynthesis(null, null))
        );
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = buildSynthesis(value, BaseType.DNA);
        Synthesis duplicateSynthesis = buildSynthesis(value, BaseType.DNA);
        synthesisService.add(synthesis);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> synthesisService.add(duplicateSynthesis)
        );
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisService.add(buildSynthesis(value, BaseType.DNA));
        synthesis.setName(synthesis.getName() + "_updated");
        synthesis.setSku(synthesis.getSku() + "_updated");
        synthesis.setBaseType(BaseType.RNA);
        Synthesis updatedSynthesis = synthesisService.update(synthesis);
        assertThat(synthesis).isEqualTo(updatedSynthesis);
        assertThat(synthesis.getName()).isEqualTo(updatedSynthesis.getName());
        assertThat(synthesis.getSku()).isEqualTo(updatedSynthesis.getSku());
        assertThat(synthesis.getBaseType()).isEqualTo(updatedSynthesis.getBaseType());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        Synthesis synthesis = synthesisService.add(buildSynthesis());
        synthesis.setName(null);
        synthesis.setSku(null);
        synthesis.setBaseType(null);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    synthesisService.update(synthesis);
                    synthesisService.findAll();
                }
        );
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = synthesisService.add(buildSynthesis(value, BaseType.DNA));
        Synthesis duplicateSynthesis = synthesisService.add(buildSynthesis(value + "_another", BaseType.DNA));
        duplicateSynthesis.setName(synthesis.getName());
        duplicateSynthesis.setSku(synthesis.getSku());
        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    synthesisService.update(duplicateSynthesis);
                    synthesisService.findAll();
                }
        );
    }

    @Test
    public void whenDelete() {
        Synthesis synthesis = synthesisService.add(buildSynthesis());
        synthesisService.delete(synthesis);
        assertThat(synthesisService.findAll()).doesNotContain(synthesis);
    }

    private Synthesis buildSynthesis(String value, BaseType baseType) {
        return Synthesis.builder()
                .name(value)
                .sku(value)
                .baseType(baseType)
                .build();
    }

    private Synthesis buildSynthesis() {
        return buildSynthesis(
                String.valueOf(System.currentTimeMillis()),
                BaseType.DNA
        );
    }
}