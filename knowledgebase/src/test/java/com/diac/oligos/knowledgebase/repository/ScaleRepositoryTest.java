package com.diac.oligos.knowledgebase.repository;

import com.diac.oligos.domain.model.Scale;
import com.diac.oligos.knowledgebase.config.DataConfig;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        ScaleRepository.class
})
public class ScaleRepositoryTest {

    @Autowired
    private ScaleRepository scaleRepository;

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = scaleRepository.save(
                Scale.builder()
                        .nanomols(1000)
                        .displayName(value)
                        .build()
        );
        assertThat(scaleRepository.findAll()).contains(scale);
    }

    @Test
    public void whenFindById() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = scaleRepository.save(
                Scale.builder()
                        .nanomols(1000)
                        .displayName(value)
                        .build()
        );
        Scale scaleInDb = scaleRepository.findById(scale.getId()).orElse(new Scale());
        assertThat(scaleInDb).isEqualTo(scale);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = scaleRepository.save(
                Scale.builder()
                        .nanomols(1000)
                        .displayName(value)
                        .build()
        );
        assertThat(value).isEqualTo(scale.getDisplayName());
        assertThat(1000).isEqualTo(scale.getNanomols());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThatThrownBy(
                () -> scaleRepository.save(
                        Scale.builder()
                                .displayName(null)
                                .build()
                )
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = Scale.builder()
                .displayName(value)
                .nanomols(1000)
                .build();
        Scale duplicateScale = Scale.builder()
                .displayName(value)
                .nanomols(1000)
                .build();
        scaleRepository.save(scale);
        assertThatThrownBy(
                () -> scaleRepository.save(duplicateScale)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }
    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = scaleRepository.save(
                Scale.builder()
                        .nanomols(1000)
                        .displayName(value)
                        .build()
        );
        scale.setNanomols(2000);
        scale.setDisplayName(scale.getDisplayName() + "_updated");
        Scale updatedScale = scaleRepository.save(scale);
        assertThat(scale).isEqualTo(updatedScale);
        assertThat(scale.getNanomols()).isEqualTo(updatedScale.getNanomols());
        assertThat(scale.getDisplayName()).isEqualTo(updatedScale.getDisplayName());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = scaleRepository.save(
                Scale.builder()
                        .nanomols(1000)
                        .displayName(value)
                        .build()
        );
        scale.setDisplayName(null);
        assertThatThrownBy(
                () -> {
                    scaleRepository.save(scale);
                    scaleRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = scaleRepository.save(
                Scale.builder()
                        .nanomols(1000)
                        .displayName(value)
                        .build()
        );
        Scale duplicateScale = scaleRepository.save(
                Scale.builder()
                        .nanomols(2000)
                        .displayName(value + "_another")
                        .build()
        );
        duplicateScale.setDisplayName(scale.getDisplayName());
        duplicateScale.setNanomols(scale.getNanomols());
        assertThatThrownBy(
                () -> {
                    scaleRepository.save(duplicateScale);
                    scaleRepository.findAll();
                }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = scaleRepository.save(
                Scale.builder()
                        .nanomols(1000)
                        .displayName(value)
                        .build()
        );
        scaleRepository.delete(scale);
        assertThat(scaleRepository.findAll()).doesNotContain(scale);
    }
}