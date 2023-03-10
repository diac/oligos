package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.FormulationPrice;
import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.priceschedule.service.FormulationPriceService;
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

@WebMvcTest(FormulationPriceController.class)
@AutoConfigureMockMvc
public class FormulationPriceControllerTest {

    private static final String URL_BASE = "/formulation_price";

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private FormulationPriceService formulationPriceService;

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
        Mockito.when(formulationPriceService.findById(id)).thenReturn(FormulationPrice.builder().id(id).build());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(formulationPriceService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(formulationPriceService.findByFormulationSku(sku))
                .thenReturn(
                        FormulationPrice.builder()
                                .id(1)
                                .formulationSku(sku)
                                .build()
                );
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenFindBySkuNotFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(formulationPriceService.findByFormulationSku(sku)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int amount = 1000;
        String sku = "qwerty";
        FormulationPrice newFormulationPrice = FormulationPrice.builder()
                .formulationSku(sku)
                .amount(amount)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        FormulationPrice savedFormulationPrice = FormulationPrice.builder()
                .id(1)
                .formulationSku(sku)
                .amount(amount)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newFormulationPrice);
        String responseBody = OBJECT_WRITER.writeValueAsString(savedFormulationPrice);
        Mockito.when(formulationPriceService.add(newFormulationPrice)).thenReturn(savedFormulationPrice);
        mockMvc.perform(
                        post(URL_BASE)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        FormulationPrice newFormulationPrice = FormulationPrice.builder()
                .formulationSku(null)
                .priceSchedule(null)
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newFormulationPrice);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        FormulationPrice newFormulationPrice = FormulationPrice.builder()
                .formulationSku("")
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newFormulationPrice);
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
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .id(id)
                .formulationSku(sku)
                .amount(amount)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(formulationPrice);
        Mockito.when(formulationPriceService.update(id, formulationPrice)).thenReturn(formulationPrice);
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
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .id(id)
                .formulationSku(null)
                .amount(0)
                .priceSchedule(null)
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(formulationPrice);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(
                put(requestUrl)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        FormulationPrice formulationPrice = FormulationPrice.builder()
                .id(id)
                .formulationSku("")
                .amount(0)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(formulationPrice);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(
                put(requestUrl)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}