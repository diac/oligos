package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.domain.model.PurificationPrice;
import com.diac.oligos.priceschedule.service.PurificationPriceService;
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

@WebMvcTest(PurificationPriceController.class)
@AutoConfigureMockMvc
public class PurificationPriceControllerTest {

    private static final String URL_BASE = "/purification_price";

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PurificationPriceService purificationPriceService;

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
        Mockito.when(purificationPriceService.findById(id))
                .thenReturn(PurificationPrice.builder().id(id).build());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(purificationPriceService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(purificationPriceService.findByPurificationSku(sku))
                .thenReturn(
                        PurificationPrice.builder()
                                .id(1)
                                .purificationSku(sku)
                                .build()
                );
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenFindBySkuNotFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(purificationPriceService.findByPurificationSku(sku)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int amount = 1000;
        String sku = "qwerty";
        PurificationPrice newPurificationPrice = PurificationPrice.builder()
                .purificationSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        PurificationPrice savedPurificationPrice = PurificationPrice.builder()
                .id(1)
                .purificationSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newPurificationPrice);
        String responseBody = OBJECT_WRITER.writeValueAsString(savedPurificationPrice);
        Mockito.when(purificationPriceService.add(newPurificationPrice)).thenReturn(savedPurificationPrice);
        mockMvc.perform(
                        post(URL_BASE)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        PurificationPrice newPurificationPrice = PurificationPrice.builder()
                .purificationSku(null)
                .scaleNanomols(1000)
                .priceSchedule(null)
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newPurificationPrice);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        PurificationPrice newPurificationPrice = PurificationPrice.builder()
                .purificationSku("")
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newPurificationPrice);
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
        PurificationPrice purificationPrice = PurificationPrice.builder()
                .id(1)
                .purificationSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(purificationPrice);
        Mockito.when(purificationPriceService.update(id, purificationPrice)).thenReturn(purificationPrice);
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
        PurificationPrice purificationPrice = PurificationPrice.builder()
                .id(1)
                .purificationSku(null)
                .amount(0)
                .scaleNanomols(1000)
                .priceSchedule(null)
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(purificationPrice);
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
        PurificationPrice purificationPrice = PurificationPrice.builder()
                .id(1)
                .purificationSku("")
                .amount(0)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(purificationPrice);
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
        Mockito.verify(purificationPriceService).delete(id);
    }
}