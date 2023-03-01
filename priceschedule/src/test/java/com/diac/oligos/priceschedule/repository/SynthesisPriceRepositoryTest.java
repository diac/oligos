package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.domain.model.SynthesisPrice;
import com.diac.oligos.priceschedule.config.DataConfig;
import jakarta.validation.ConstraintViolationException;
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
        SynthesisPriceRepository.class,
        PriceScheduleRepository.class
})
public class SynthesisPriceRepositoryTest {

    @Autowired
    private SynthesisPriceRepository synthesisPriceRepository;

    @Autowired
    private PriceScheduleRepository priceScheduleRepository;

    @Test
    public void whenFindAll() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        assertThat(synthesisPriceRepository.findAll()).contains(synthesisPrice);
    }

    @Test
    public void whenFindById() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        SynthesisPrice synthesisPriceInDb = synthesisPriceRepository
                .findById(synthesisPrice.getId())
                .orElse(new SynthesisPrice());
        assertThat(synthesisPriceInDb).isEqualTo(synthesisPrice);
    }

    @Test
    public void whenFindBySynthesisSku() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        SynthesisPrice synthesisPriceInDb = synthesisPriceRepository
                .findBySynthesisSku(synthesisPrice.getSynthesisSku())
                .orElse(new SynthesisPrice());
        assertThat(synthesisPriceInDb).isEqualTo(synthesisPrice);
    }

    @Test
    public void whenAdd() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        assertThat(amount).isEqualTo(synthesisPrice.getAmount());
        assertThat(amount).isEqualTo(synthesisPrice.getScaleNanomols());
        assertThat(str).isEqualTo(synthesisPrice.getSynthesisSku());
    }

    @Test
    public void whenAddWithNullValuesThenThrowException() {
        assertThatThrownBy(
                () -> synthesisPriceRepository.save(
                        SynthesisPrice.builder()
                                .synthesisSku(null)
                                .priceSchedule(null)
                                .build()
                )
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build();
        SynthesisPrice duplicateSynthesisPrice = SynthesisPrice.builder()
                .amount(amount)
                .synthesisSku(str)
                .scaleNanomols(amount)
                .priceSchedule(priceSchedule)
                .build();
        synthesisPriceRepository.save(synthesisPrice);
        assertThatThrownBy(
                () -> synthesisPriceRepository.save(duplicateSynthesisPrice)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenAddWithNegativeAmountThenThrowException() {
        int amount = -1;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = SynthesisPrice.builder()
                .amount(amount)
                .synthesisSku(str)
                .scaleNanomols(amount)
                .priceSchedule(priceSchedule)
                .build();
        assertThatThrownBy(
                () -> synthesisPriceRepository.save(synthesisPrice)
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdate() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        synthesisPrice.setAmount(synthesisPrice.getAmount() + 1000);
        synthesisPrice.setScaleNanomols(synthesisPrice.getScaleNanomols() + 1000);
        synthesisPrice.setSynthesisSku(synthesisPrice.getSynthesisSku() + "_updated");
        SynthesisPrice updatedSynthesisPrice = synthesisPriceRepository.save(synthesisPrice);
        assertThat(synthesisPrice).isEqualTo(updatedSynthesisPrice);
        assertThat(synthesisPrice.getAmount()).isEqualTo(updatedSynthesisPrice.getAmount());
        assertThat(synthesisPrice.getScaleNanomols()).isEqualTo(updatedSynthesisPrice.getScaleNanomols());
        assertThat(synthesisPrice.getSynthesisSku()).isEqualTo(updatedSynthesisPrice.getSynthesisSku());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        synthesisPrice.setSynthesisSku(null);
        synthesisPrice.setPriceSchedule(null);
        assertThatThrownBy(
                () -> {
                    synthesisPriceRepository.save(synthesisPrice);
                    synthesisPriceRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        SynthesisPrice duplicateSynthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount + 1000)
                        .synthesisSku(str + "_amount")
                        .scaleNanomols(amount + 1000)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        duplicateSynthesisPrice.setAmount(synthesisPrice.getAmount());
        duplicateSynthesisPrice.setSynthesisSku(synthesisPrice.getSynthesisSku());
        duplicateSynthesisPrice.setScaleNanomols(synthesisPrice.getScaleNanomols());
        assertThatThrownBy(
                () -> {
                    synthesisPriceRepository.save(duplicateSynthesisPrice);
                    synthesisPriceRepository.findAll();
                }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenDelete() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        SynthesisPrice synthesisPrice = synthesisPriceRepository.save(
                SynthesisPrice.builder()
                        .amount(amount)
                        .synthesisSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        synthesisPriceRepository.delete(synthesisPrice);
        assertThat(synthesisPriceRepository.findAll()).doesNotContain(synthesisPrice);
    }
}