package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.model.ModificationPrice;
import com.diac.oligos.domain.model.PriceSchedule;
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
        ModificationPriceRepository.class,
        PriceScheduleRepository.class
})
public class ModificationPriceRepositoryTest {

    @Autowired
    private ModificationPriceRepository modificationPriceRepository;

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
        ModificationPrice modificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount)
                        .modificationSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        assertThat(modificationPriceRepository.findAll()).contains(modificationPrice);
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
        ModificationPrice modificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount)
                        .modificationSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        ModificationPrice modificationPriceInDb = modificationPriceRepository
                .findById(modificationPrice.getId())
                .orElse(new ModificationPrice());
        assertThat(modificationPriceInDb).isEqualTo(modificationPrice);
    }

    @Test
    public void whenFindByModificationSku() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        ModificationPrice modificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount)
                        .modificationSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        ModificationPrice modificationPriceInDb = modificationPriceRepository
                .findByModificationSku(modificationPrice.getModificationSku())
                .orElse(new ModificationPrice());
        assertThat(modificationPriceInDb).isEqualTo(modificationPrice);
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
        ModificationPrice modificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount)
                        .modificationSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        assertThat(amount).isEqualTo(modificationPrice.getAmount());
        assertThat(amount).isEqualTo(modificationPrice.getScaleNanomols());
        assertThat(str).isEqualTo(modificationPrice.getModificationSku());
    }

    @Test
    public void whenAddWithNullValuesThenThrowException() {
        assertThatThrownBy(
                () -> modificationPriceRepository.save(
                        ModificationPrice.builder()
                                .modificationSku(null)
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
        ModificationPrice modificationPrice = ModificationPrice.builder()
                .amount(amount)
                .modificationSku(str)
                .scaleNanomols(amount)
                .priceSchedule(priceSchedule)
                .build();
        ModificationPrice duplicateModificationPrice = ModificationPrice.builder()
                .amount(amount)
                .modificationSku(str)
                .scaleNanomols(amount)
                .priceSchedule(priceSchedule)
                .build();
        modificationPriceRepository.save(modificationPrice);
        assertThatThrownBy(
                () -> modificationPriceRepository.save(duplicateModificationPrice)
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
        ModificationPrice modificationPrice = ModificationPrice.builder()
                .amount(amount)
                .modificationSku(str)
                .scaleNanomols(amount)
                .priceSchedule(priceSchedule)
                .build();
        assertThatThrownBy(
                () -> modificationPriceRepository.save(modificationPrice)
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
        ModificationPrice modificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount)
                        .modificationSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        modificationPrice.setAmount(modificationPrice.getAmount() + 1000);
        modificationPrice.setScaleNanomols(modificationPrice.getScaleNanomols() + 1000);
        modificationPrice.setModificationSku(modificationPrice.getModificationSku() + "_updated");
        ModificationPrice updatedModificationPrice = modificationPriceRepository.save(modificationPrice);
        assertThat(modificationPrice).isEqualTo(updatedModificationPrice);
        assertThat(modificationPrice.getAmount()).isEqualTo(updatedModificationPrice.getAmount());
        assertThat(modificationPrice.getScaleNanomols()).isEqualTo(updatedModificationPrice.getScaleNanomols());
        assertThat(modificationPrice.getModificationSku()).isEqualTo(updatedModificationPrice.getModificationSku());
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
        ModificationPrice modificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount)
                        .modificationSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        modificationPrice.setModificationSku(null);
        modificationPrice.setPriceSchedule(null);
        assertThatThrownBy(
                () -> {
                    modificationPriceRepository.save(modificationPrice);
                    modificationPriceRepository.findAll();
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
        ModificationPrice modificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount)
                        .modificationSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        ModificationPrice duplicateModificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount + 1000)
                        .modificationSku(str + "_another")
                        .scaleNanomols(amount + 1000)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        duplicateModificationPrice.setAmount(modificationPrice.getAmount());
        duplicateModificationPrice.setScaleNanomols(modificationPrice.getScaleNanomols());
        duplicateModificationPrice.setModificationSku(modificationPrice.getModificationSku());
        assertThatThrownBy(
                () -> {
                    modificationPriceRepository.save(duplicateModificationPrice);
                    modificationPriceRepository.findAll();
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
        ModificationPrice modificationPrice = modificationPriceRepository.save(
                ModificationPrice.builder()
                        .amount(amount)
                        .modificationSku(str)
                        .scaleNanomols(amount)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        modificationPriceRepository.delete(modificationPrice);
        assertThat(modificationPriceRepository.findAll()).doesNotContain(modificationPrice);
    }
}