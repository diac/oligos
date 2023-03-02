package com.diac.oligos.priceschedule.service;

import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.priceschedule.repository.PriceScheduleRepository;
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
        PriceScheduleJpaService.class
})
public class PriceScheduleJpaServiceTest {

    @Autowired
    private PriceScheduleService priceScheduleService;

    @MockBean
    private PriceScheduleRepository priceScheduleRepository;

    @Test
    public void whenFindAll() {
        List<PriceSchedule> expectedPriceSchedules = List.of(
                PriceSchedule.builder()
                        .id(1)
                        .build(),
                PriceSchedule.builder()
                        .id(2)
                        .build()
        );
        Mockito.when(priceScheduleRepository.findAll()).thenReturn(expectedPriceSchedules);
        List<PriceSchedule> priceSchedules = priceScheduleService.findAll();
        Mockito.verify(priceScheduleRepository).findAll();
        assertThat(priceSchedules).isEqualTo(expectedPriceSchedules);
    }

    @Test
    public void whenFindById() {
        int id = 1;
        PriceSchedule expectedPriceSchedule = PriceSchedule.builder()
                .id(id)
                .build();
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.of(expectedPriceSchedule));
        PriceSchedule priceSchedule = priceScheduleService.findById(id);
        assertThat(priceSchedule).isEqualTo(expectedPriceSchedule);
    }

    @Test
    public void whenFindByIdNotFoundThenThrowException() {
        int id = 1;
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceScheduleService.findById(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenFindEffective() {
        PriceSchedule expectedPriceSchedule = PriceSchedule.builder()
                .id(1)
                .effective(true)
                .build();
        Mockito.when(priceScheduleRepository.findFirstByEffectiveTrue()).thenReturn(Optional.of(expectedPriceSchedule));
        PriceSchedule priceSchedule = priceScheduleService.findEffective();
        Mockito.verify(priceScheduleRepository).findFirstByEffectiveTrue();
        assertThat(priceSchedule).isEqualTo(expectedPriceSchedule);
    }

    @Test
    public void whenFindEffectiveNotFoundThenThrowException() {
        Mockito.when(priceScheduleRepository.findFirstByEffectiveTrue()).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceScheduleService.findEffective()
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenAdd() {
        int id = 1;
        PriceSchedule expectedPriceSchedule = PriceSchedule.builder()
                .id(id)
                .build();
        Mockito.when(priceScheduleRepository.save(expectedPriceSchedule)).thenReturn(expectedPriceSchedule);
        PriceSchedule priceSchedule = priceScheduleService.add(expectedPriceSchedule);
        Mockito.verify(priceScheduleRepository).save(expectedPriceSchedule);
        assertThat(priceSchedule).isEqualTo(expectedPriceSchedule);
    }

    @Test
    public void whenUpdate() {
        int id = 1;
        PriceSchedule priceSchedule = PriceSchedule.builder()
                .id(id)
                .build();
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.of(priceSchedule));
        Mockito.when(priceScheduleRepository.save(priceSchedule)).thenReturn(priceSchedule);
        PriceSchedule updatedPriceSchedule = priceScheduleService.update(id, priceSchedule);
        Mockito.verify(priceScheduleRepository).findById(id);
        Mockito.verify(priceScheduleRepository).save(priceSchedule);
        assertThat(priceSchedule).isEqualTo(updatedPriceSchedule);
    }

    @Test
    public void whenUpdateNonExistentThenThrowException() {
        int id = 1;
        PriceSchedule priceSchedule = PriceSchedule.builder()
                .id(id)
                .build();
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceScheduleService.update(id, priceSchedule)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenDelete() {
        int id = 1;
        PriceSchedule priceSchedule = PriceSchedule.builder()
                .id(id)
                .build();
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.of(priceSchedule));
        priceScheduleService.delete(id);
        Mockito.verify(priceScheduleRepository).findById(id);
        Mockito.verify(priceScheduleRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceScheduleService.delete(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenMakeEffective() {
        int id = 1;
        PriceSchedule priceSchedule = PriceSchedule.builder()
                .id(id)
                .effective(true)
                .build();
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.of(priceSchedule));
        priceScheduleService.makeEffective(id);
        Mockito.verify(priceScheduleRepository).save(priceSchedule);
    }

    @Test
    public void whenMakeEffectiveOnNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceScheduleService.makeEffective(id)
        ).isInstanceOf(NoResultException.class);
    }

    @Test
    public void whenMakeIneffective() {
        int id = 1;
        PriceSchedule priceSchedule = PriceSchedule.builder()
                .id(id)
                .effective(false)
                .build();
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.of(priceSchedule));
        priceScheduleService.makeIneffective(id);
        Mockito.verify(priceScheduleRepository).save(priceSchedule);
    }

    @Test
    public void whenMakeIneffectiveOnNonExistentThenThrowException() {
        int id = 1;
        Mockito.when(priceScheduleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> priceScheduleService.makeIneffective(id)
        ).isInstanceOf(NoResultException.class);
    }
}