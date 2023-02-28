package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Synthesis;
import com.diac.oligos.knowledgebase.repository.SynthesisRepository;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {
        SynthesisJpaService.class
})
public class SynthesisJpaServiceTest {

    @Autowired
    private SynthesisService synthesisService;

    @MockBean
    private SynthesisRepository synthesisRepository;

    @Test
    public void whenFindAll() {
        List<Synthesis> expectedSyntheses = List.of(
                Synthesis.builder()
                        .id(1)
                        .build(),
                Synthesis.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(synthesisRepository.findAll()).thenReturn(expectedSyntheses);
        List<Synthesis> syntheses = synthesisService.findAll();
        Mockito.verify(synthesisRepository).findAll();
        assertThat(syntheses).isEqualTo(expectedSyntheses);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        Synthesis expectedSynthesis = Synthesis.builder()
                .id(id)
                .build();
        Mockito.when(synthesisRepository.findById(id)).thenReturn(Optional.of(expectedSynthesis));
        Synthesis synthesis = synthesisService.findById(id);
        Mockito.verify(synthesisRepository).findById(id);
        assertThat(synthesis).isEqualTo(expectedSynthesis);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(synthesisRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> synthesisService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindBySku() {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis expectedSynthesis = Synthesis.builder()
                .id(id)
                .sku(value)
                .build();
        Mockito.when(synthesisRepository.findBySku(value)).thenReturn(Optional.of(expectedSynthesis));
        Synthesis synthesis = synthesisService.findBySku(value);
        Mockito.verify(synthesisRepository).findBySku(value);
        assertThat(synthesis).isEqualTo(expectedSynthesis);
    }

    @Test
    public void whenFindBySkuNotFoundThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Mockito.when(synthesisRepository.findBySku(value)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> synthesisService.findBySku(value)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        Synthesis synthesis = Synthesis.builder()
                .id(id)
                .build();
        Mockito.when(synthesisRepository.save(synthesis)).thenReturn(synthesis);
        Synthesis savedSynthesis = synthesisService.add(synthesis);
        Mockito.verify(synthesisRepository).save(synthesis);
        assertThat(savedSynthesis).isEqualTo(synthesis);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        Synthesis synthesis = Synthesis.builder()
                .id(id)
                .build();
        Mockito.when(synthesisRepository.findById(id)).thenReturn(Optional.of(synthesis));
        Mockito.when(synthesisRepository.save(synthesis)).thenReturn(synthesis);
        Synthesis savedSynthesis = synthesisService.update(id, synthesis);
        Mockito.verify(synthesisRepository).findById(id);
        Mockito.verify(synthesisRepository).save(synthesis);
        assertThat(savedSynthesis).isEqualTo(synthesis);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        Synthesis synthesis = Synthesis.builder()
                .id(id)
                .build();
        Mockito.when(synthesisRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> synthesisService.update(id, synthesis)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        Synthesis synthesis = Synthesis.builder()
                .id(id)
                .build();
        Mockito.when(synthesisRepository.findById(id)).thenReturn(Optional.of(synthesis));
        synthesisService.delete(id);
        Mockito.verify(synthesisRepository).findById(id);
        Mockito.verify(synthesisRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(synthesisRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> synthesisService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}