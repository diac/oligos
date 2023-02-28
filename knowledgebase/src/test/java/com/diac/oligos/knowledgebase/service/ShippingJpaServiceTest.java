package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Shipping;
import com.diac.oligos.knowledgebase.repository.ShippingRepository;
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
        ShippingJpaService.class
})
public class ShippingJpaServiceTest {

    @Autowired
    private ShippingService shippingService;

    @MockBean
    private ShippingRepository shippingRepository;

    @Test
    public void whenFindAll() {
        List<Shipping> expectedShippings = List.of(
                Shipping.builder()
                        .id(1)
                        .build(),
                Shipping.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(shippingRepository.findAll()).thenReturn(expectedShippings);
        List<Shipping> shippings = shippingService.findAll();
        Mockito.verify(shippingRepository).findAll();
        assertThat(shippings).isEqualTo(expectedShippings);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        Shipping expectedShipping = Shipping.builder()
                .id(id)
                .build();
        Mockito.when(shippingRepository.findById(id)).thenReturn(Optional.of(expectedShipping));
        Shipping shipping = shippingService.findById(id);
        Mockito.verify(shippingRepository).findById(id);
        assertThat(shipping).isEqualTo(expectedShipping);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(shippingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> shippingService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        Shipping shipping = Shipping.builder()
                .id(id)
                .build();
        Mockito.when(shippingRepository.save(shipping)).thenReturn(shipping);
        Shipping savedShipping = shippingService.add(shipping);
        Mockito.verify(shippingRepository).save(shipping);
        assertThat(savedShipping).isEqualTo(shipping);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        Shipping shipping = Shipping.builder()
                .id(id)
                .build();
        Mockito.when(shippingRepository.findById(id)).thenReturn(Optional.of(shipping));
        Mockito.when(shippingRepository.save(shipping)).thenReturn(shipping);
        Shipping savedShipping = shippingService.update(id, shipping);
        Mockito.verify(shippingRepository).findById(id);
        Mockito.verify(shippingRepository).save(shipping);
        assertThat(savedShipping).isEqualTo(shipping);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        Shipping shipping = Shipping.builder()
                .id(id)
                .build();
        Mockito.when(shippingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> shippingService.update(id, shipping)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        Shipping shipping = Shipping.builder()
                .id(id)
                .build();
        Mockito.when(shippingRepository.findById(id)).thenReturn(Optional.of(shipping));
        shippingService.delete(id);
        Mockito.verify(shippingRepository).findById(id);
        Mockito.verify(shippingRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(shippingRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> shippingService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }
}