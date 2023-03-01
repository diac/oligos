package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.priceschedule.config.DataConfig;
import jakarta.validation.ConstraintViolationException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        PriceScheduleRepository.class
})
public class PriceScheduleRepositoryTest {

    @Autowired
    private PriceScheduleRepository priceScheduleRepository;

    @Test
    public void whenFindAll() {
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        assertThat(priceScheduleRepository.findAll()).contains(priceSchedule);
    }

    @Test
    public void whenFindById() {
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        PriceSchedule priceScheduleInDb = priceScheduleRepository
                .findById(priceSchedule.getId())
                .orElse(new PriceSchedule());
        assertThat(priceScheduleInDb).isEqualTo(priceSchedule);
    }

    @Test
    public void whenAdd() {
        String str = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(now)
                        .effective(false)
                        .build()
        );
        assertThat(str).isEqualTo(priceSchedule.getName());
        assertThat(now).isEqualTo(priceSchedule.getCreated());
        assertThat(priceSchedule.isEffective()).isFalse();
    }

    @Test
    public void whenAddWithNullValuesThenThrowException() {
        assertThatThrownBy(
                () -> priceScheduleRepository.save(
                        PriceSchedule.builder()
                                .name(null)
                                .created(null)
                                .effective(false)
                                .build()
                )
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String str = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        PriceSchedule priceSchedule =
                PriceSchedule.builder()
                        .name(str)
                        .created(now)
                        .effective(false)
                        .build();
        PriceSchedule duplicatePriceSchedule = PriceSchedule.builder()
                .name(str)
                .created(now)
                .effective(false)
                .build();
        priceScheduleRepository.save(priceSchedule);
        assertThatThrownBy(
                () -> priceScheduleRepository.save(duplicatePriceSchedule)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenUpdate() {
        String str = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(now)
                        .effective(false)
                        .build()
        );
        priceSchedule.setName(priceSchedule.getName() + "_updated");
        priceSchedule.setCreated(priceSchedule.getCreated().plusDays(1));
        priceSchedule.setEffective(true);
        PriceSchedule updatedPriceSchedule = priceScheduleRepository.save(priceSchedule);
        assertThat(priceSchedule).isEqualTo(updatedPriceSchedule);
        assertThat(priceSchedule.getName()).isEqualTo(updatedPriceSchedule.getName());
        assertThat(priceSchedule.getCreated()).isEqualTo(updatedPriceSchedule.getCreated());
        assertThat(priceSchedule.isEffective()).isEqualTo(updatedPriceSchedule.isEffective());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String str = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(now)
                        .effective(false)
                        .build()
        );
        priceSchedule.setName(null);
        priceSchedule.setCreated(null);
        assertThatThrownBy(
                () -> {
                    priceScheduleRepository.save(priceSchedule);
                    priceScheduleRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String str = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(now)
                        .effective(false)
                        .build()
        );
        PriceSchedule duplicatePriceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str + "_another")
                        .created(now)
                        .effective(false)
                        .build()
        );
        duplicatePriceSchedule.setName(priceSchedule.getName());
        assertThatThrownBy(
                () -> {
                    priceScheduleRepository.save(duplicatePriceSchedule);
                    priceScheduleRepository.findAll();
                }
        );
    }

    @Test
    public void whenDelete() {
        String str = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(now)
                        .effective(false)
                        .build()
        );
        priceScheduleRepository.delete(priceSchedule);
        assertThat(priceScheduleRepository.findAll()).doesNotContain(priceSchedule);
    }
}