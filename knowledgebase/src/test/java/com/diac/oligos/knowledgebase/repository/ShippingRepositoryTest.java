package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Shipping;
import com.diac.oligos.knowledgebase.config.DataConfig;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        ShippingRepository.class
})
public class ShippingRepositoryTest {

    @Autowired
    private ShippingRepository shippingRepository;

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingRepository.save(
                Shipping.builder()
                        .name(value)
                        .build()
        );
        assertThat(shippingRepository.findAll()).contains(shipping);
    }

    @Test
    public void whenFindById() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingRepository.save(
                Shipping.builder()
                        .name(value)
                        .build()
        );
        Shipping shippingInDb = shippingRepository.findById(shipping.getId()).orElse(new Shipping());
        assertThat(shippingInDb).isEqualTo(shipping);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingRepository.save(
                Shipping.builder()
                        .name(value)
                        .build()
        );
        assertThat(value).isEqualTo(shipping.getName());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThatThrownBy(
                () -> shippingRepository.save(
                        Shipping.builder()
                                .name(null)
                                .build()
                )
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = Shipping.builder()
                .name(value)
                .build();
        Shipping duplicateShipping = Shipping.builder()
                .name(value)
                .build();
        shippingRepository.save(shipping);
        assertThatThrownBy(
                () -> shippingRepository.save(duplicateShipping)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingRepository.save(
                Shipping.builder()
                        .name(value)
                        .build()
        );
        shipping.setName(shipping.getName() + "_updated");
        Shipping updatedShipping = shippingRepository.save(shipping);
        assertThat(shipping).isEqualTo(updatedShipping);
        assertThat(shipping.getName()).isEqualTo(updatedShipping.getName());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingRepository.save(
                Shipping.builder()
                        .name(value)
                        .build()
        );
        shipping.setName(null);
        assertThatThrownBy(
                () -> {
                    shippingRepository.save(shipping);
                    shippingRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingRepository.save(
                Shipping.builder()
                        .name(value)
                        .build()
        );
        Shipping duplicateShipping = shippingRepository.save(
                Shipping.builder()
                        .name(value + "_another")
                        .build()
        );
        duplicateShipping.setName(shipping.getName());
        assertThatThrownBy(
                () -> {
                    shippingRepository.save(duplicateShipping);
                    shippingRepository.findAll();
                }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingRepository.save(
                Shipping.builder()
                        .name(value)
                        .build()
        );
        shippingRepository.delete(shipping);
        assertThat(shippingRepository.findAll()).doesNotContain(shipping);
    }
}