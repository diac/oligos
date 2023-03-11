package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.domain.model.SynthesisPrice;
import com.diac.oligos.priceschedule.service.SynthesisPriceService;
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

@WebMvcTest(SynthesisPriceController.class)
@AutoConfigureMockMvc
public class SynthesisPriceControllerTest {

    private static final String URL_BASE = "/synthesis_price";

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private SynthesisPriceService synthesisPriceService;

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
        Mockito.when(synthesisPriceService.findById(id))
                .thenReturn(SynthesisPrice.builder().id(id).build());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(synthesisPriceService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(synthesisPriceService.findBySynthesisSku(sku))
                .thenReturn(
                        SynthesisPrice.builder()
                                .id(1)
                                .synthesisSku(sku)
                                .build()
                );
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenFindBySkuNotFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(synthesisPriceService.findBySynthesisSku(sku)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int amount = 1000;
        String sku = "qwerty";
        SynthesisPrice newSynthesisPrice = SynthesisPrice.builder()
                .synthesisSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        SynthesisPrice savedSynthesisPrice = SynthesisPrice.builder()
                .id(1)
                .synthesisSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newSynthesisPrice);
        String responseBody = OBJECT_WRITER.writeValueAsString(savedSynthesisPrice);
        Mockito.when(synthesisPriceService.add(newSynthesisPrice)).thenReturn(savedSynthesisPrice);
        mockMvc.perform(
                        post(URL_BASE)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        SynthesisPrice newSynthesisPrice = SynthesisPrice.builder()
                .synthesisSku(null)
                .amount(0)
                .scaleNanomols(1000)
                .priceSchedule(null)
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newSynthesisPrice);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        SynthesisPrice newSynthesisPrice = SynthesisPrice.builder()
                .synthesisSku("")
                .amount(0)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newSynthesisPrice);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        int amount = 1000;
        String sku = "qwerty";
        SynthesisPrice synthesisPrice = SynthesisPrice.builder()
                .synthesisSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(synthesisPrice);
        Mockito.when(synthesisPriceService.update(id, synthesisPrice)).thenReturn(synthesisPrice);
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
        SynthesisPrice synthesisPrice = SynthesisPrice.builder()
                .synthesisSku(null)
                .amount(0)
                .scaleNanomols(1000)
                .priceSchedule(null)
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(synthesisPrice);
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
        int amount = 1000;
        SynthesisPrice synthesisPrice = SynthesisPrice.builder()
                .synthesisSku("")
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(synthesisPrice);
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
        Mockito.verify(synthesisPriceService).delete(id);
    }
}