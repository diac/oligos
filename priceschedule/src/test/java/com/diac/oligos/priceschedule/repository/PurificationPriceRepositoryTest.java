package com.diac.oligos.priceschedule.repository;

import com.diac.oligos.domain.enumeration.BaseType;
import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.domain.model.PurificationPrice;
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
        PurificationPriceRepository.class,
        PriceScheduleRepository.class
})
public class PurificationPriceRepositoryTest {

    @Autowired
    private PurificationPriceRepository purificationPriceRepository;

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
        PurificationPrice purificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount)
                        .purificationSku(str)
                        .scaleNanomols(amount)
                        .baseType(BaseType.DNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        assertThat(purificationPriceRepository.findAll()).contains(purificationPrice);
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
        PurificationPrice purificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount)
                        .purificationSku(str)
                        .scaleNanomols(amount)
                        .baseType(BaseType.DNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        PurificationPrice purificationPriceInDb = purificationPriceRepository
                .findById(purificationPrice.getId())
                .orElse(new PurificationPrice());
        assertThat(purificationPriceInDb).isEqualTo(purificationPrice);
    }

    @Test
    public void whenFindByPurificationSku() {
        int amount = 1000;
        String str = String.valueOf(System.currentTimeMillis());
        PriceSchedule priceSchedule = priceScheduleRepository.save(
                PriceSchedule.builder()
                        .name(str)
                        .created(LocalDateTime.now())
                        .effective(false)
                        .build()
        );
        PurificationPrice purificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount)
                        .purificationSku(str)
                        .scaleNanomols(amount)
                        .baseType(BaseType.DNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        PurificationPrice purificationPriceInDb = purificationPriceRepository
                .findByPurificationSku(purificationPrice.getPurificationSku())
                .orElse(new PurificationPrice());
        assertThat(purificationPriceInDb).isEqualTo(purificationPrice);
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
        PurificationPrice purificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount)
                        .purificationSku(str)
                        .scaleNanomols(amount)
                        .baseType(BaseType.DNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        assertThat(amount).isEqualTo(purificationPrice.getAmount());
        assertThat(amount).isEqualTo(purificationPrice.getScaleNanomols());
        assertThat(str).isEqualTo(purificationPrice.getPurificationSku());
    }

    @Test
    public void whenAddWithNullValuesThenThrowException() {
        assertThatThrownBy(
                () -> purificationPriceRepository.save(
                        PurificationPrice.builder()
                                .purificationSku(null)
                                .baseType(null)
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
        PurificationPrice purificationPrice = PurificationPrice.builder()
                .amount(amount)
                .purificationSku(str)
                .scaleNanomols(amount)
                .baseType(BaseType.DNA)
                .priceSchedule(priceSchedule)
                .build();
        PurificationPrice duplicatePurificationPrice = PurificationPrice.builder()
                .amount(amount)
                .purificationSku(str)
                .scaleNanomols(amount)
                .baseType(BaseType.DNA)
                .priceSchedule(priceSchedule)
                .build();
        purificationPriceRepository.save(purificationPrice);
        assertThatThrownBy(
                () -> purificationPriceRepository.save(duplicatePurificationPrice)
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
        PurificationPrice purificationPrice = PurificationPrice.builder()
                .amount(amount)
                .purificationSku(str)
                .scaleNanomols(amount)
                .baseType(BaseType.DNA)
                .priceSchedule(priceSchedule)
                .build();
        assertThatThrownBy(
                () -> purificationPriceRepository.save(purificationPrice)
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
        PurificationPrice purificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount)
                        .purificationSku(str)
                        .scaleNanomols(amount)
                        .baseType(BaseType.DNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        purificationPrice.setAmount(purificationPrice.getAmount() + 1000);
        purificationPrice.setPurificationSku(purificationPrice.getPurificationSku() + "_updated");
        purificationPrice.setScaleNanomols(purificationPrice.getScaleNanomols() + 1000);
        purificationPrice.setBaseType(BaseType.RNA);
        PurificationPrice updatedPurificationPrice = purificationPriceRepository.save(purificationPrice);
        assertThat(purificationPrice).isEqualTo(updatedPurificationPrice);
        assertThat(purificationPrice.getAmount()).isEqualTo(updatedPurificationPrice.getAmount());
        assertThat(purificationPrice.getPurificationSku()).isEqualTo(updatedPurificationPrice.getPurificationSku());
        assertThat(purificationPrice.getScaleNanomols()).isEqualTo(updatedPurificationPrice.getScaleNanomols());
        assertThat(purificationPrice.getBaseType()).isEqualTo(updatedPurificationPrice.getBaseType());
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
        PurificationPrice purificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount)
                        .purificationSku(str)
                        .scaleNanomols(amount)
                        .baseType(BaseType.DNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        purificationPrice.setPurificationSku(null);
        purificationPrice.setBaseType(null);
        purificationPrice.setPriceSchedule(null);
        assertThatThrownBy(
                () -> {
                    purificationPriceRepository.save(purificationPrice);
                    purificationPriceRepository.findAll();
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
        PurificationPrice purificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount)
                        .purificationSku(str)
                        .scaleNanomols(amount)
                        .baseType(BaseType.DNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        PurificationPrice duplicatePurificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount + 1000)
                        .purificationSku(str + "_another")
                        .scaleNanomols(amount + 1000)
                        .baseType(BaseType.RNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        duplicatePurificationPrice.setAmount(purificationPrice.getAmount());
        duplicatePurificationPrice.setPurificationSku(purificationPrice.getPurificationSku());
        duplicatePurificationPrice.setScaleNanomols(purificationPrice.getScaleNanomols());
        duplicatePurificationPrice.setBaseType(purificationPrice.getBaseType());
        assertThatThrownBy(
                () -> {
                    purificationPriceRepository.save(duplicatePurificationPrice);
                    purificationPriceRepository.findAll();
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
        PurificationPrice purificationPrice = purificationPriceRepository.save(
                PurificationPrice.builder()
                        .amount(amount)
                        .purificationSku(str)
                        .scaleNanomols(amount)
                        .baseType(BaseType.DNA)
                        .priceSchedule(priceSchedule)
                        .build()
        );
        purificationPriceRepository.delete(purificationPrice);
        assertThat(purificationPriceRepository.findAll()).doesNotContain(purificationPrice);
    }
}