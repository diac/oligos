package com.diac.oligos.priceschedule.controller;

import com.diac.oligos.domain.model.ModificationPrice;
import com.diac.oligos.domain.model.PriceSchedule;
import com.diac.oligos.priceschedule.service.ModificationPriceService;
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

@WebMvcTest(ModificationPriceController.class)
@AutoConfigureMockMvc
public class ModificationPriceControllerTest {

    private static final String URL_BASE = "/modification_price";

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ModificationPriceService modificationPriceService;

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
        Mockito.when(modificationPriceService.findById(id))
                .thenReturn(ModificationPrice.builder().id(id).build());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(modificationPriceService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(modificationPriceService.findByModificationSku(sku))
                .thenReturn(
                        ModificationPrice.builder()
                                .id(1)
                                .modificationSku(sku)
                                .build()
                );
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenFindBySkuNotFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(modificationPriceService.findByModificationSku(sku)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int amount = 1000;
        String sku = "qwerty";
        ModificationPrice newModificationPrice = ModificationPrice.builder()
                .modificationSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        ModificationPrice savedModificationPrice = ModificationPrice.builder()
                .id(1)
                .modificationSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newModificationPrice);
        String responseBody = OBJECT_WRITER.writeValueAsString(savedModificationPrice);
        Mockito.when(modificationPriceService.add(newModificationPrice)).thenReturn(savedModificationPrice);
        mockMvc.perform(
                        post(URL_BASE)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        ModificationPrice newModificationPrice = ModificationPrice.builder()
                .modificationSku(null)
                .priceSchedule(null)
                .scaleNanomols(1000)
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newModificationPrice);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        ModificationPrice newModificationPrice = ModificationPrice.builder()
                .modificationSku("")
                .amount(0)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String requestBody = OBJECT_WRITER.writeValueAsString(newModificationPrice);
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
        ModificationPrice modificationPrice = ModificationPrice.builder()
                .id(id)
                .modificationSku(sku)
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(modificationPrice);
        Mockito.when(modificationPriceService.update(id, modificationPrice)).thenReturn(modificationPrice);
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
        ModificationPrice modificationPrice = ModificationPrice.builder()
                .id(id)
                .modificationSku(null)
                .amount(0)
                .scaleNanomols(1000)
                .priceSchedule(null)
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(modificationPrice);
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
        ModificationPrice modificationPrice = ModificationPrice.builder()
                .id(id)
                .modificationSku("")
                .amount(amount)
                .scaleNanomols(1000)
                .priceSchedule(PriceSchedule.builder().id(1).build())
                .build();
        String jsonValue = OBJECT_WRITER.writeValueAsString(modificationPrice);
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
    }
}