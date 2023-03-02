package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.SynthesisPrice;
import com.diac.oligos.priceschedule.repository.SynthesisPriceRepository;
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
        SynthesisPriceJpaService.class
})
public class SynthesisPriceJpaServiceTest {

    @Autowired
    private SynthesisPriceService synthesisPriceService;

    @MockBean
    private SynthesisPriceRepository synthesisPriceRepository;

    @Test
    public void whenFindAll() {
        List<SynthesisPrice> expectedSynthesisPrices = List.of(
                SynthesisPrice.builder()
                        .id(1)
                        .build(),
                SynthesisPrice.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(synthesisPriceRepository.findAll()).thenReturn(expectedSynthesisPrices);
        List<SynthesisPrice> synthesisPrices = synthesisPriceService.findAll();
        Mockito.verify(synthesisPriceRepository).findAll();
        assertThat(synthesisPrices).isEqualTo(expectedSynthesisPrices);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        SynthesisPrice expectedSynthesisPrice = SynthesisPrice.builder()
                .id(id)
                .build();
        Mockito.when(synthesisPriceRepository.findById(id)).thenReturn(Optional.of(expectedSynthesisPrice));
        SynthesisPrice synthesisPrice = synthesisPriceService.findById(id);
        Mockito.verify(synthesisPriceRepository).findById(id);
        assertThat(synthesisPrice).isEqualTo(expectedSynthesisPrice);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(synthesisPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> synthesisPriceService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindBySynthesisSku() {
        int id = 1;
        String str = String.valueOf(System.currentTimeMillis());
        SynthesisPrice expectedSynthesisPrice = SynthesisPrice.builder()
                .id(id)
                .synthesisSku(str)
                .build();
        Mockito.when(synthesisPriceRepository.findBySynthesisSku(str)).thenReturn(Optional.of(expectedSynthesisPrice));
        SynthesisPrice synthesisPrice = synthesisPriceService.findBySynthesisSku(str);
        Mockito.verify(synthesisPriceRepository).findBySynthesisSku(str);
        assertThat(synthesisPrice).isEqualTo(expectedSynthesisPrice);
    }

    @Test
    public void whenFindBySynthesisSkuNotFoundThenThrowException() {
        String str = String.valueOf(System.currentTimeMillis());
        Mockito.when(synthesisPriceRepository.findBySynthesisSku(str)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> synthesisPriceService.findBySynthesisSku(str)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        SynthesisPrice expectedSynthesisPrice = SynthesisPrice.builder()
                .id(id)
                .build();
        Mockito.when(synthesisPriceRepository.save(expectedSynthesisPrice)).thenReturn(expectedSynthesisPrice);
        SynthesisPrice synthesisPrice = synthesisPriceService.add(expectedSynthesisPrice);
        Mockito.verify(synthesisPriceRepository).save(expectedSynthesisPrice);
        assertThat(synthesisPrice).isEqualTo(expectedSynthesisPrice);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        SynthesisPrice synthesisPrice = SynthesisPrice.builder()
                .id(id)
                .build();
        Mockito.when(synthesisPriceRepository.findById(id)).thenReturn(Optional.of(synthesisPrice));
        Mockito.when(synthesisPriceRepository.save(synthesisPrice)).thenReturn(synthesisPrice);
        SynthesisPrice updatedSynthesisPrice = synthesisPriceService.update(id, synthesisPrice);
        Mockito.verify(synthesisPriceRepository).findById(id);
        Mockito.verify(synthesisPriceRepository).save(synthesisPrice);
        assertThat(synthesisPrice).isEqualTo(updatedSynthesisPrice);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        SynthesisPrice synthesisPrice = SynthesisPrice.builder()
                .id(id)
                .build();
        Mockito.when(synthesisPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> synthesisPriceService.update(id, synthesisPrice)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        SynthesisPrice synthesisPrice = SynthesisPrice.builder()
                .id(id)
                .build();
        Mockito.when(synthesisPriceRepository.findById(id)).thenReturn(Optional.of(synthesisPrice));
        synthesisPriceService.delete(id);
        Mockito.verify(synthesisPriceRepository).findById(id);
        Mockito.verify(synthesisPriceRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(synthesisPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> synthesisPriceService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}