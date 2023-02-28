package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Shipping;
import com.diac.oligos.knowledgebase.service.ShippingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShippingController.class)
@AutoConfigureMockMvc
public class ShippingControllerTest {

    private static final String URL_BASE = "/shipping";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingService shippingService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(shippingService.findById(id)).thenReturn(Optional.of(Shipping.builder().id(id).build()));
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(shippingService.findById(id)).thenReturn(Optional.empty());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Shipping newShipping = Shipping.builder()
                .name(value)
                .build();
        Shipping savedShipping = Shipping.builder()
                .id(id)
                .name(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newShipping);
        String responseBody = objectWriter.writeValueAsString(savedShipping);
        Mockito.when(shippingService.add(newShipping)).thenReturn(savedShipping);
        mockMvc.perform(
                        post(URL_BASE)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        Shipping shipping = Shipping.builder()
                .name(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(shipping);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Shipping shipping = Shipping.builder()
                .name("")
                .build();
        String requestBody = objectWriter.writeValueAsString(shipping);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Shipping shipping = Shipping.builder()
                .id(id)
                .name(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(shipping);
        Mockito.when(shippingService.update(id, shipping)).thenReturn(shipping);
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
        Shipping shipping = Shipping.builder()
                .id(1)
                .name(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(shipping);
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
        Shipping shipping = Shipping.builder()
                .id(1)
                .name(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(shipping);
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