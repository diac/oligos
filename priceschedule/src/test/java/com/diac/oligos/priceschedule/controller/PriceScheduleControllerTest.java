package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.priceschedule.service.PriceScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceScheduleController.class)
@AutoConfigureMockMvc
public class PriceScheduleControllerTest {

    private static final String URL_BASE = "/price_schedule";

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PriceScheduleService priceScheduleService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(priceScheduleService.findById(id))
                .thenReturn(PriceSchedule.builder().id(id).build());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(priceScheduleService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetEffectiveFound() throws Exception {
        int id = 1;
        Mockito.when(priceScheduleService.findEffective())
                .thenReturn(PriceSchedule.builder().id(id).build());
        String requestUrl = String.format("%s/effective", URL_BASE);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetEffectiveNotFound() throws Exception {
        Mockito.when(priceScheduleService.findEffective()).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/effective", URL_BASE);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String name = "qwerty";
        PriceSchedule newPriceSchedule = PriceSchedule.builder()
                .name(name)
                .build();
        PriceSchedule savedPriceSchedule = PriceSchedule.builder()
                .id(id)
                .name(name)
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newPriceSchedule);
        String responseBody = OBJECT_WRITER.writeValueAsString(savedPriceSchedule);
        Mockito.when(priceScheduleService.add(newPriceSchedule)).thenReturn(savedPriceSchedule);
        mockMvc.perform(
                        post(URL_BASE)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        PriceSchedule newPriceSchedule = PriceSchedule.builder()
                .name(null)
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newPriceSchedule);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        PriceSchedule newPriceSchedule = PriceSchedule.builder()
                .name("")
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newPriceSchedule);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String name = "qwerty";
        PriceSchedule priceSchedule = PriceSchedule.builder()
                .name(name)
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(priceSchedule);
        Mockito.when(priceScheduleService.update(id, priceSchedule)).thenReturn(priceSchedule);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(
                        put(requestUrl)
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(jsonValue));
    }

    @Test
    public void whenPutWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        PriceSchedule priceSchedule = PriceSchedule.builder()
                .name(null)
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(priceSchedule);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        PriceSchedule priceSchedule = PriceSchedule.builder()
                .name("")
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(priceSchedule);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
        Mockito.verify(priceScheduleService).delete(id);
    }

    @Test
    public void whenMakeEffective() throws Exception {
        int id = 1;
        String requestUrl = String.format("%s/%d/make_effective", URL_BASE, id);
        mockMvc.perform(patch(requestUrl))
                .andExpect(status().isOk());
        Mockito.verify(priceScheduleService).makeEffective(id);
    }

    @Test
    public void whenMakeIneffective() throws Exception {
        int id = 1;
        String requestUrl = String.format("%s/%d/make_ineffective", URL_BASE, id);
        mockMvc.perform(patch(requestUrl))
                .andExpect(status().isOk());
        Mockito.verify(priceScheduleService).makeIneffective(id);
    }
}