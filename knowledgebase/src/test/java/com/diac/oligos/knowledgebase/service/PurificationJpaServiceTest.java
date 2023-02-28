package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Purification;
import com.diac.oligos.knowledgebase.repository.PurificationRepository;
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
        PurificationJpaService.class
})
public class PurificationJpaServiceTest {

    @Autowired
    private PurificationService purificationService;

    @MockBean
    private PurificationRepository purificationRepository;

    @Test
    public void whenFindAll() {
        List<Purification> expectedPurifications = List.of(
                Purification.builder()
                        .id(1)
                        .build(),
                Purification.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(purificationRepository.findAll()).thenReturn(expectedPurifications);
        List<Purification> purifications = purificationService.findAll();
        Mockito.verify(purificationRepository).findAll();
        assertThat(purifications).isEqualTo(expectedPurifications);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        Purification expectedPurification = Purification.builder()
                .id(1)
                .build();
        Mockito.when(purificationRepository.findById(id)).thenReturn(Optional.of(expectedPurification));
        Purification purification = purificationService.findById(id);
        Mockito.verify(purificationRepository).findById(id);
        assertThat(purification).isEqualTo(expectedPurification);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(purificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> purificationService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindBySku() {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Purification expectedPurification = Purification.builder()
                .id(id)
                .sku(value)
                .build();
        Mockito.when(purificationRepository.findBySku(value)).thenReturn(Optional.of(expectedPurification));
        Purification purification = purificationService.findBySku(value);
        Mockito.verify(purificationRepository).findBySku(value);
        assertThat(purification).isEqualTo(expectedPurification);
    }

    @Test
    public void whenFindBySkuNotFoundThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Mockito.when(purificationRepository.findBySku(value)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> purificationService.findBySku(value)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        Purification purification = Purification.builder()
                .id(id)
                .build();
        Mockito.when(purificationRepository.save(purification)).thenReturn(purification);
        Purification savedPurification = purificationService.add(purification);
        Mockito.verify(purificationRepository).save(purification);
        assertThat(savedPurification).isEqualTo(purification);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        Purification purification = Purification.builder()
                .id(1)
                .build();
        Mockito.when(purificationRepository.findById(id)).thenReturn(Optional.of(purification));
        Mockito.when(purificationRepository.save(purification)).thenReturn(purification);
        Purification savedPurification = purificationService.update(id, purification);
        Mockito.verify(purificationRepository).findById(id);
        Mockito.verify(purificationRepository).save(purification);
        assertThat(savedPurification).isEqualTo(purification);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        Purification purification = Purification.builder()
                .id(1)
                .build();
        Mockito.when(purificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> purificationService.update(id, purification)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        Purification purification = Purification.builder()
                .id(1)
                .build();
        Mockito.when(purificationRepository.findById(id)).thenReturn(Optional.of(purification));
        purificationService.delete(id);
        Mockito.verify(purificationRepository).findById(id);
        Mockito.verify(purificationRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(purificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> purificationService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}