package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.ModificationPrice;
import com.diac.oligos.priceschedule.repository.ModificationPriceRepository;
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
        ModificationPriceJpaService.class
})
public class ModificationPriceJpaServiceTest {

    @Autowired
    private ModificationPriceService modificationPriceService;

    @MockBean
    private ModificationPriceRepository modificationPriceRepository;

    @Test
    public void whenFindAll() {
        List<ModificationPrice> expectedModificationPrices = List.of(
                ModificationPrice.builder()
                        .id(1)
                        .build(),
                ModificationPrice.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(modificationPriceRepository.findAll()).thenReturn(expectedModificationPrices);
        List<ModificationPrice> modificationPrices = modificationPriceService.findAll();
        Mockito.verify(modificationPriceRepository).findAll();
        assertThat(modificationPrices).isEqualTo(expectedModificationPrices);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        ModificationPrice expectedModificationPrice = ModificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(modificationPriceRepository.findById(id)).thenReturn(Optional.of(expectedModificationPrice));
        ModificationPrice modificationPrice = modificationPriceService.findById(id);
        Mockito.verify(modificationPriceRepository).findById(id);
        assertThat(modificationPrice).isEqualTo(expectedModificationPrice);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(modificationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> modificationPriceService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindByModificationSku() {
        int id = 1;
        String str = String.valueOf(System.currentTimeMillis());
        ModificationPrice expectedModificationPrice = ModificationPrice.builder()
                .id(id)
                .modificationSku(str)
                .build();
        Mockito.when(modificationPriceRepository.findByModificationSku(str))
                .thenReturn(Optional.of(expectedModificationPrice));
        ModificationPrice modificationPrice = modificationPriceService.findByModificationSku(str);
        Mockito.verify(modificationPriceRepository).findByModificationSku(str);
        assertThat(modificationPrice).isEqualTo(expectedModificationPrice);
    }

    @Test
    public void whenFindByModificationSkuAndNotFoundThenThrowException() {
        String str = String.valueOf(System.currentTimeMillis());
        Mockito.when(modificationPriceRepository.findByModificationSku(str)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> modificationPriceService.findByModificationSku(str)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        ModificationPrice expectedModificationPrice = ModificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(modificationPriceRepository.save(expectedModificationPrice)).thenReturn(expectedModificationPrice);
        ModificationPrice modificationPrice = modificationPriceService.add(expectedModificationPrice);
        Mockito.verify(modificationPriceRepository).save(expectedModificationPrice);
        assertThat(modificationPrice).isEqualTo(expectedModificationPrice);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        ModificationPrice modificationPrice = ModificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(modificationPriceRepository.findById(id)).thenReturn(Optional.of(modificationPrice));
        Mockito.when(modificationPriceRepository.save(modificationPrice)).thenReturn(modificationPrice);
        ModificationPrice updatedModificationPrice = modificationPriceService.update(id, modificationPrice);
        Mockito.verify(modificationPriceRepository).findById(id);
        Mockito.verify(modificationPriceRepository).save(modificationPrice);
        assertThat(modificationPrice).isEqualTo(updatedModificationPrice);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        ModificationPrice modificationPrice = ModificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(modificationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> modificationPriceService.update(id, modificationPrice)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        ModificationPrice modificationPrice = ModificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(modificationPriceRepository.findById(id)).thenReturn(Optional.of(modificationPrice));
        modificationPriceService.delete(id);
        Mockito.verify(modificationPriceRepository).findById(id);
        Mockito.verify(modificationPriceRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(modificationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> modificationPriceService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}