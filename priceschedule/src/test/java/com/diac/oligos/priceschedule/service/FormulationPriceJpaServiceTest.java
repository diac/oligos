package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.FormulationPrice;
import com.diac.oligos.priceschedule.repository.FormulationPriceRepository;
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
        FormulationPriceJpaService.class
})
public class FormulationPriceJpaServiceTest {

    @Autowired
    private FormulationPriceService formulationPriceService;

    @MockBean
    private FormulationPriceRepository formulationPriceRepository;

    @Test
    public void whenFindAll() {
        List<FormulationPrice> expectedFormulationPrices = List.of(
                FormulationPrice.builder()
                        .id(1)
                        .build(),
                FormulationPrice.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(formulationPriceRepository.findAll()).thenReturn(expectedFormulationPrices);
        List<FormulationPrice> formulationPrices = formulationPriceService.findAll();
        Mockito.verify(formulationPriceRepository).findAll();
        assertThat(formulationPrices).isEqualTo(expectedFormulationPrices);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        FormulationPrice expectedFormulationPrice = FormulationPrice.builder()
                .id(id)
                .build();
        Mockito.when(formulationPriceRepository.findById(id)).thenReturn(Optional.of(expectedFormulationPrice));
        FormulationPrice formulationPrice = formulationPriceService.findById(id);
        Mockito.verify(formulationPriceRepository).findById(id);
        assertThat(formulationPrice).isEqualTo(expectedFormulationPrice);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(formulationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> formulationPriceService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindByFormulationSku() {
        int id = 1;
        String str = String.valueOf(System.currentTimeMillis());
        FormulationPrice expectedFormulationPrice = FormulationPrice.builder()
                .id(id)
                .formulationSku(str)
                .build();
        Mockito.when(formulationPriceRepository.findByFormulationSku(str))
                .thenReturn(Optional.of(expectedFormulationPrice));
        FormulationPrice formulationPrice = formulationPriceService.findByFormulationSku(str);
        Mockito.verify(formulationPriceRepository).findByFormulationSku(str);
        assertThat(formulationPrice).isEqualTo(expectedFormulationPrice);
    }

    @Test
    public void whenFindByFormulationSkuAndNotFoundThenThrowException() {
        String str = String.valueOf(System.currentTimeMillis());
        Mockito.when(formulationPriceRepository.findByFormulationSku(str)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> formulationPriceService.findByFormulationSku(str)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        FormulationPrice expectedFormulationPrice = FormulationPrice.builder()
                .id(id)
                .build();
        Mockito.when(formulationPriceRepository.save(expectedFormulationPrice)).thenReturn(expectedFormulationPrice);
        FormulationPrice formulationPrice = formulationPriceService.add(expectedFormulationPrice);
        Mockito.verify(formulationPriceRepository).save(expectedFormulationPrice);
        assertThat(formulationPrice).isEqualTo(expectedFormulationPrice);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .id(id)
                .build();
        Mockito.when(formulationPriceRepository.findById(id)).thenReturn(Optional.of(formulationPrice));
        Mockito.when(formulationPriceRepository.save(formulationPrice)).thenReturn(formulationPrice);
        FormulationPrice updatedFormulationPrice = formulationPriceService.update(id, formulationPrice);
        Mockito.verify(formulationPriceRepository).findById(id);
        Mockito.verify(formulationPriceRepository).save(formulationPrice);
        assertThat(formulationPrice).isEqualTo(updatedFormulationPrice);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .id(id)
                .build();
        Mockito.when(formulationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> formulationPriceService.update(id, formulationPrice)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .id(id)
                .build();
        Mockito.when(formulationPriceRepository.findById(id)).thenReturn(Optional.of(formulationPrice));
        formulationPriceService.delete(id);
        Mockito.verify(formulationPriceRepository).findById(id);
        Mockito.verify(formulationPriceRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(formulationPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> formulationPriceService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}