package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.FormulationPrice;
import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.priceschedule.config.DataConfig;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        FormulationPriceRepository.class,
        PriceScheduleRepository.class
})
public class FormulationPriceRepositoryTest {

    @Autowired
    private FormulationPriceRepository formulationPriceRepository;

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
        FormulationPrice formulationPrice = formulationPriceRepository.save(
                FormulationPrice.builder()
                        .amount(amount)
                        .formulationSku(str)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        assertThat(formulationPriceRepository.findAll()).contains(formulationPrice);
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
        FormulationPrice formulationPrice = formulationPriceRepository.save(
                FormulationPrice.builder()
                        .amount(amount)
                        .formulationSku(str)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        FormulationPrice formulationPriceInDb = formulationPriceRepository
                .findById(formulationPrice.getId())
                .orElse(new FormulationPrice());
        assertThat(formulationPriceInDb).isEqualTo(formulationPrice);
    }

    @Test
    public void whenFindByFormulationSku() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        FormulationPrice formulationPrice = formulationPriceRepository.save(
                FormulationPrice.builder()
                        .amount(amount)
                        .formulationSku(str)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        FormulationPrice formulationPriceInDb = formulationPriceRepository
                .findByFormulationSku(formulationPrice.getFormulationSku())
                .orElse(new FormulationPrice());
        assertThat(formulationPriceInDb).isEqualTo(formulationPrice);
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
        FormulationPrice formulationPrice = formulationPriceRepository.save(
                FormulationPrice.builder()
                        .amount(amount)
                        .formulationSku(str)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        assertThat(amount).isEqualTo(formulationPrice.getAmount());
        assertThat(str).isEqualTo(formulationPrice.getFormulationSku());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThatThrownBy(
                () -> formulationPriceRepository.save(
                        FormulationPrice.builder()
                                .amount(0)
                                .formulationSku(null)
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
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .amount(amount)
                .formulationSku(str)
                .priceSchedule(priceSchedule)
                .build();
        FormulationPrice duplicateFormulationPrice = FormulationPrice.builder()
                .amount(amount)
                .formulationSku(str)
                .priceSchedule(priceSchedule)
                .build();
        formulationPriceRepository.save(formulationPrice);
        assertThatThrownBy(
                () -> formulationPriceRepository.save(duplicateFormulationPrice)
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
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .amount(amount)
                .formulationSku(str)
                .priceSchedule(priceSchedule)
                .build();
        assertThatThrownBy(
                () -> formulationPriceRepository.save(formulationPrice)
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
        FormulationPrice formulationPrice = formulationPriceRepository.save(
                FormulationPrice.builder()
                        .amount(amount)
                        .formulationSku(str)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        formulationPrice.setAmount(formulationPrice.getAmount() + 1000);
        formulationPrice.setFormulationSku(formulationPrice.getFormulationSku() + "_updated");
        FormulationPrice updatedFormulationPrice = formulationPriceRepository.save(formulationPrice);
        assertThat(formulationPrice).isEqualTo(updatedFormulationPrice);
        assertThat(formulationPrice.getAmount()).isEqualTo(updatedFormulationPrice.getAmount());
        assertThat(formulationPrice.getFormulationSku()).isEqualTo(updatedFormulationPrice.getFormulationSku());
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
        FormulationPrice formulationPrice = formulationPriceRepository.save(
                FormulationPrice.builder()
                        .amount(amount)
                        .formulationSku(str)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        formulationPrice.setFormulationSku(null);
        formulationPrice.setPriceSchedule(null);
        assertThatThrownBy(
                () -> {
                    formulationPriceRepository.save(formulationPrice);
                    formulationPriceRepository.findAll();
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
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .amount(amount)
                .formulationSku(str)
                .priceSchedule(priceSchedule)
                .build();
        FormulationPrice duplicateFormulationPrice = FormulationPrice.builder()
                .amount(amount)
                .formulationSku(str)
                .priceSchedule(priceSchedule)
                .build();
        formulationPriceRepository.save(formulationPrice);
        assertThatThrownBy(
                () -> {
                    formulationPriceRepository.save(duplicateFormulationPrice);
                    formulationPriceRepository.findAll();
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
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .amount(amount)
                .formulationSku(str)
                .priceSchedule(priceSchedule)
                .build();
        formulationPriceRepository.delete(formulationPrice);
        assertThat(formulationPriceRepository.findAll()).doesNotContain(formulationPrice);
    }
}