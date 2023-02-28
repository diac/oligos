package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Modification;
import com.diac.oligos.knowledgebase.repository.ModificationRepository;
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
        ModificationJpaService.class
})
public class ModificationJpaServiceTest {

    @Autowired
    private ModificationService modificationService;

    @MockBean
    private ModificationRepository modificationRepository;

    @Test
    public void whenFindAll() {
        List<Modification> expectedModifications = List.of(
                Modification.builder()
                        .id(1)
                        .build(),
                Modification.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(modificationRepository.findAll()).thenReturn(expectedModifications);
        List<Modification> modifications = modificationService.findAll();
        Mockito.verify(modificationRepository).findAll();
        assertThat(modifications).isEqualTo(expectedModifications);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        Modification expectedModification = Modification.builder()
                .id(id)
                .build();
        Mockito.when(modificationRepository.findById(id)).thenReturn(Optional.of(expectedModification));
        Modification modification = modificationService.findById(id);
        Mockito.verify(modificationRepository).findById(id);
        assertThat(modification).isEqualTo(expectedModification);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(modificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> modificationService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindBySku() {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Modification expectedModification = Modification.builder()
                .id(id)
                .sku(value)
                .build();
        Mockito.when(modificationRepository.findBySku(value)).thenReturn(Optional.of(expectedModification));
        Modification modification = modificationService.findBySku(value);
        Mockito.verify(modificationRepository).findBySku(value);
        assertThat(modification).isEqualTo(expectedModification);
    }

    @Test
    public void whenFindBySkuNotFoundThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Mockito.when(modificationRepository.findBySku(value)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> modificationService.findBySku(value)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        Modification modification = Modification.builder()
                .id(id)
                .build();
        Mockito.when(modificationRepository.save(modification)).thenReturn(modification);
        Modification savedModification = modificationService.add(modification);
        Mockito.verify(modificationRepository).save(modification);
        assertThat(savedModification).isEqualTo(modification);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        Modification modification = Modification.builder()
                .id(id)
                .build();
        Mockito.when(modificationRepository.findById(id)).thenReturn(Optional.of(modification));
        Mockito.when(modificationRepository.save(modification)).thenReturn(modification);
        Modification savedModification = modificationService.update(id, modification);
        Mockito.verify(modificationRepository).findById(id);
        Mockito.verify(modificationRepository).save(modification);
        assertThat(savedModification).isEqualTo(modification);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        Modification modification = Modification.builder()
                .id(id)
                .build();
        Mockito.when(modificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> modificationService.update(id, modification)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        Modification modification = Modification.builder()
                .id(id)
                .build();
        Mockito.when(modificationRepository.findById(id)).thenReturn(Optional.of(modification));
        modificationService.delete(id);
        Mockito.verify(modificationRepository).findById(id);
        Mockito.verify(modificationRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(modificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> modificationService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}