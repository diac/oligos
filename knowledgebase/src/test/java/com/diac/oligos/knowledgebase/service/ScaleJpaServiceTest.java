package com.diac.oligos.knowledgebase.service;

import com.diac.oligos.domain.model.Scale;
import com.diac.oligos.knowledgebase.config.DataConfig;
import com.diac.oligos.knowledgebase.repository.ScaleRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        ScaleRepository.class,
        ScaleJpaService.class
})
public class ScaleJpaServiceTest {

    @Autowired
    private ScaleRepository scaleRepository;

    private ScaleService scaleService;

    @BeforeEach
    public void init() {
        scaleService = new ScaleJpaService(scaleRepository);
    }

    @Test
    public void whenFindAll() {
        Scale scale = scaleService.add(buildScale());
        assertThat(scaleService.findAll()).contains(scale);
    }

    @Test
    public void whenFindById() {
        Scale scale = scaleService.add(buildScale());
        Scale scaleInDb = scaleService.findById(scale.getId()).orElse(new Scale());
        assertThat(scaleInDb).isEqualTo(scale);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        int number = new Random().nextInt()  & Integer.MAX_VALUE;
        Scale scale = scaleService.add(buildScale(value, number));
        assertThat(value).isEqualTo(scale.getDisplayName());
        assertThat(number).isEqualTo(scale.getNanomols());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThrows(
                ConstraintViolationException.class,
                () -> scaleService.add(buildScale(null, 0))
        );
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = buildScale(value, 1000);
        Scale duplicateScale = buildScale(value, 1000);
        scaleService.add(scale);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> scaleService.add(duplicateScale)
        );
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        int number = new Random().nextInt()  & Integer.MAX_VALUE;
        Scale scale = scaleService.add(buildScale(value, number));
        scale.setDisplayName(scale.getDisplayName() + "_updated");
        scale.setNanomols(scale.getNanomols() + 1000);
        Scale updatedScale = scaleService.update(scale);
        assertThat(scale).isEqualTo(updatedScale);
        assertThat(scale.getDisplayName()).isEqualTo(updatedScale.getDisplayName());
        assertThat(scale.getNanomols()).isEqualTo(updatedScale.getNanomols());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        Scale scale = scaleService.add(buildScale());
        scale.setDisplayName(null);
        assertThrows(
                ConstraintViolationException.class,
                () -> {
                    scaleService.update(scale);
                    scaleService.findAll();
                }
        );
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = scaleService.add(buildScale(value, 1000));
        Scale duplicateScale = scaleService.add(buildScale(value + "_another", 2000));
        duplicateScale.setDisplayName(scale.getDisplayName());
        duplicateScale.setNanomols(scale.getNanomols());
        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    scaleService.update(duplicateScale);
                    scaleService.findAll();
                }
        );
    }

    @Test
    public void whenDelete() {
        Scale scale = scaleService.add(buildScale());
        scaleService.delete(scale);
        assertThat(scaleService.findAll()).doesNotContain(scale);
    }

    private Scale buildScale(String value, int number) {
        return Scale.builder()
                .displayName(value)
                .nanomols(number)
                .build();
    }

    private Scale buildScale() {
        return buildScale(
                String.valueOf(System.currentTimeMillis()),
                new Random().nextInt()  & Integer.MAX_VALUE
        );
    }
}