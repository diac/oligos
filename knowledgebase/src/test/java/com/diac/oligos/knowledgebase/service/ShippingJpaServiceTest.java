package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Shipping;
import com.diac.oligos.knowledgebase.config.DataConfig;
import com.diac.oligos.knowledgebase.repository.ShippingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        ShippingRepository.class,
        ShippingJpaService.class
})
public class ShippingJpaServiceTest {

    @Autowired
    private ShippingRepository shippingRepository;

    private ShippingService shippingService;

    @BeforeEach
    public void init() {
        shippingService = new ShippingJpaService(shippingRepository);
    }

    @Test
    public void whenFindAll() {
        Shipping shipping = shippingService.add(buildShipping());
        assertThat(shippingService.findAll()).contains(shipping);
    }

    @Test
    public void whenFindById() {
        Shipping shipping = shippingService.add(buildShipping());
        Shipping shippingInDb = shippingService.findById(shipping.getId()).orElse(new Shipping());
        assertThat(shippingInDb).isEqualTo(shipping);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingService.add(buildShipping(value));
        assertThat(value).isEqualTo(shipping.getName());
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = shippingService.add(buildShipping(value));
        shipping.setName(shipping.getName() + "_updated");
        Shipping updatedShipping = shippingService.update(shipping);
        assertThat(shipping).isEqualTo(updatedShipping);
        assertThat(shipping.getName()).isEqualTo(updatedShipping.getName());
    }

    @Test
    public void whenDelete() {
        Shipping shipping = shippingService.add(buildShipping());
        shippingService.delete(shipping);
        assertThat(shippingService.findAll()).doesNotContain(shipping);
    }

    private Shipping buildShipping(String value) {
        return Shipping.builder()
                .name(value)
                .build();
    }

    private Shipping buildShipping() {
        return buildShipping(String.valueOf(System.currentTimeMillis()));
    }
}