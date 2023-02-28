package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Scale;
import com.diac.oligos.knowledgebase.repository.ScaleRepository;
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
        ScaleJpaService.class
})
public class ScaleJpaServiceTest {

    @Autowired
    private ScaleService scaleService;

    @MockBean
    private ScaleRepository scaleRepository;

    @Test
    public void whenFindAll() {
        List<Scale> expectedScales = List.of(
                Scale.builder()
                        .id(1)
                        .build(),
                Scale.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(scaleRepository.findAll()).thenReturn(expectedScales);
        List<Scale> scales = scaleService.findAll();
        Mockito.verify(scaleRepository).findAll();
        assertThat(scales).isEqualTo(expectedScales);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        Scale expectedScale = Scale.builder()
                .id(1)
                .build();
        Mockito.when(scaleRepository.findById(id)).thenReturn(Optional.of(expectedScale));
        Scale scale = scaleService.findById(id);
        Mockito.verify(scaleRepository).findById(id);
        assertThat(scale).isEqualTo(expectedScale);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(scaleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> scaleService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        Scale scale = Scale.builder()
                .id(id)
                .build();
        Mockito.when(scaleRepository.save(scale)).thenReturn(scale);
        Scale savedScale = scaleService.add(scale);
        Mockito.verify(scaleRepository).save(scale);
        assertThat(savedScale).isEqualTo(scale);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        Scale scale = Scale.builder()
                .id(1)
                .build();
        Mockito.when(scaleRepository.findById(id)).thenReturn(Optional.of(scale));
        Mockito.when(scaleRepository.save(scale)).thenReturn(scale);
        Scale savedScale = scaleService.update(id, scale);
        Mockito.verify(scaleRepository).findById(id);
        Mockito.verify(scaleRepository).save(scale);
        assertThat(savedScale).isEqualTo(scale);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        Scale scale = Scale.builder()
                .id(1)
                .build();
        Mockito.when(scaleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> scaleService.update(id, scale)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        Scale scale = Scale.builder()
                .id(1)
                .build();
        Mockito.when(scaleRepository.findById(id)).thenReturn(Optional.of(scale));
        scaleService.delete(id);
        Mockito.verify(scaleRepository).findById(id);
        Mockito.verify(scaleRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(scaleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> scaleService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}