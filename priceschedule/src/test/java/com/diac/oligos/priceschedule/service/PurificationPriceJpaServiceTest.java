package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.PurificationPrice;
import com.diac.oligos.priceschedule.repository.PurificationPriceRepository;
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
        PurificationPriceJpaService.class
})
public class PurificationPriceJpaServiceTest {

    @Autowired
    private PurificationPriceService purificationPriceService;

    @MockBean
    private PurificationPriceRepository purificationPriceRepository;

    @Test
    public void whenFindAll() {
        List<PurificationPrice> expectedPurificationPrices = List.of(
                PurificationPrice.builder()
                        .id(1)
                        .build(),
                PurificationPrice.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(purificationPriceRepository.findAll()).thenReturn(expectedPurificationPrices);
        List<PurificationPrice> purificationPrices = purificationPriceService.findAll();
        Mockito.verify(purificationPriceRepository).findAll();
        assertThat(purificationPrices).isEqualTo(expectedPurificationPrices);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        PurificationPrice expectedPurificationPrice = PurificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(purificationPriceRepository.findById(id)).thenReturn(Optional.of(expectedPurificationPrice));
        PurificationPrice purificationPrice = purificationPriceService.findById(id);
        Mockito.verify(purificationPriceRepository).findById(id);
        assertThat(purificationPrice).isEqualTo(expectedPurificationPrice);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(purificationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> purificationPriceService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindByPurificationSku() {
        int id = 1;
        String str = String.valueOf(System.currentTimeMillis());
        PurificationPrice expectedPurificationPrice = PurificationPrice.builder()
                .id(id)
                .purificationSku(str)
                .build();
        Mockito.when(purificationPriceRepository.findByPurificationSku(str))
                .thenReturn(Optional.of(expectedPurificationPrice));
        PurificationPrice purificationPrice = purificationPriceService.findByPurificationSku(str);
        Mockito.verify(purificationPriceRepository).findByPurificationSku(str);
        assertThat(purificationPrice).isEqualTo(expectedPurificationPrice);
    }

    @Test
    public void whenFindByPurificationSkuNotFoundThenThrowException() {
        String str = String.valueOf(System.currentTimeMillis());
        Mockito.when(purificationPriceRepository.findByPurificationSku(str))
                .thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> purificationPriceService.findByPurificationSku(str)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        PurificationPrice expectedPurificationPrice = PurificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(purificationPriceRepository.save(expectedPurificationPrice)).thenReturn(expectedPurificationPrice);
        PurificationPrice purificationPrice = purificationPriceService.add(expectedPurificationPrice);
        Mockito.verify(purificationPriceRepository).save(expectedPurificationPrice);
        assertThat(purificationPrice).isEqualTo(expectedPurificationPrice);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        PurificationPrice purificationPrice = PurificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(purificationPriceRepository.findById(id)).thenReturn(Optional.of(purificationPrice));
        Mockito.when(purificationPriceRepository.save(purificationPrice)).thenReturn(purificationPrice);
        PurificationPrice updatedPurificationPrice = purificationPriceService.update(id, purificationPrice);
        Mockito.verify(purificationPriceRepository).findById(id);
        Mockito.verify(purificationPriceRepository).save(purificationPrice);
        assertThat(purificationPrice).isEqualTo(updatedPurificationPrice);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        PurificationPrice purificationPrice = PurificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(purificationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> purificationPriceService.update(id, purificationPrice)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        PurificationPrice purificationPrice = PurificationPrice.builder()
                .id(id)
                .build();
        Mockito.when(purificationPriceRepository.findById(id)).thenReturn(Optional.of(purificationPrice));
        purificationPriceService.delete(id);
        Mockito.verify(purificationPriceRepository).findById(id);
        Mockito.verify(purificationPriceRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(purificationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> purificationPriceService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}