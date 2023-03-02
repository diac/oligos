package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Formulation;
import com.diac.oligos.knowledgebase.repository.FormulationRepository;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(classes = {
        FormulationJpaService.class
})
public class FormulationJpaServiceTest {

    @Autowired
    private FormulationService formulationService;

    @MockBean
    private FormulationRepository formulationRepository;

    @Test
    public void whenFindAll() {
        List<Formulation> formulations = List.of(
                Formulation.builder()
                        .id(1)
                        .build(),
                Formulation.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(formulationRepository.findAll()).thenReturn(formulations);
        assertThat(formulationService.findAll()).isEqualTo(formulations);
        Mockito.verify(formulationRepository).findAll();
    }

    @Test
    public void whenFindById() {
        int id = 1;
        Formulation formulation =  Formulation.builder()
                .id(id)
                .build();
        Mockito.when(formulationRepository.findById(id)).thenReturn(Optional.of(formulation));
        assertThat(formulationService.findById(id)).isEqualTo(formulation);
        Mockito.verify(formulationRepository).findById(id);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(formulationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> formulationService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindBySku() {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation =  Formulation.builder()
                .id(id)
                .sku(value)
                .build();
        Mockito.when(formulationRepository.findBySku(value)).thenReturn(Optional.of(formulation));
        assertThat(formulationService.findBySku(value)).isEqualTo(formulation);
        Mockito.verify(formulationRepository).findBySku(value);
    }

    @Test
    public void whenFindBySkuNotFoundThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Mockito.when(formulationRepository.findBySku(value)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> formulationService.findBySku(value)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        Formulation formulation =  Formulation.builder()
                .id(id)
                .build();
        Mockito.when(formulationRepository.save(formulation)).thenReturn(formulation);
        assertThat(formulationService.add(formulation)).isEqualTo(formulation);
        Mockito.verify(formulationRepository).save(formulation);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        Formulation formulation =  Formulation.builder()
                .id(id)
                .build();
        Mockito.when(formulationRepository.findById(id)).thenReturn(Optional.of(formulation));
        Mockito.when(formulationRepository.save(formulation)).thenReturn(formulation);
        assertThat(formulationService.update(id, formulation)).isEqualTo(formulation);
        Mockito.verify(formulationRepository).save(formulation);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        Formulation formulation =  Formulation.builder()
                .id(id)
                .build();
        Mockito.when(formulationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> formulationService.update(id, formulation)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        Formulation formulation =  Formulation.builder()
                .id(id)
                .build();
        Mockito.when(formulationRepository.findById(id)).thenReturn(Optional.of(formulation));
        formulationService.delete(id);
        Mockito.verify(formulationRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(formulationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> formulationService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}