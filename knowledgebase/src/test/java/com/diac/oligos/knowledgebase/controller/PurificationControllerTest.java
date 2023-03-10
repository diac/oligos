package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Purification;
import com.diac.oligos.knowledgebase.service.PurificationService;
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

@WebMvcTest(PurificationController.class)
@AutoConfigureMockMvc
public class PurificationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PurificationService purificationService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(get("/purification"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(purificationService.findById(id)).thenReturn(Purification.builder().id(id).build());
        String requestUrl = String.format("/purification/%d", id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(purificationService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("/purification/%d", id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(purificationService.findBySku(sku))
                .thenReturn(
                        Purification.builder()
                                .id(1)
                                .sku(sku)
                                .build()
                );
        String requestUrl = String.format("/purification/find_by_sku/%s", sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenFindBySkuNotFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(purificationService.findBySku(sku)).thenThrow(NoResultException.class);
        String requestUrl = String.format("/purification/find_by_sku/%s", sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Purification newPurification = Purification.builder()
                .name(value)
                .sku(value)
                .build();
        Purification savedPurification = Purification.builder()
                .id(id)
                .name(value)
                .sku(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newPurification);
        String responseBody = objectWriter.writeValueAsString(savedPurification);
        Mockito.when(purificationService.add(newPurification)).thenReturn(savedPurification);
        mockMvc.perform(
                        post("/purification")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        Purification purification = Purification.builder()
                .name(null)
                .sku(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(purification);
        mockMvc.perform(
                post("/purification")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Purification purification = Purification.builder()
                .name("")
                .sku("")
                .build();
        String requestBody = objectWriter.writeValueAsString(purification);
        mockMvc.perform(
                post("/purification")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Purification purification = Purification.builder()
                .id(id)
                .name(value)
                .sku(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(purification);
        Mockito.when(purificationService.update(id, purification)).thenReturn(purification);
        mockMvc.perform(
                        put("/purification/1")
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(jsonValue));
    }

    @Test
    public void whenPutWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        Purification purification = Purification.builder()
                .id(1)
                .name(null)
                .sku(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(purification);
        mockMvc.perform(
                put("/purification/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Purification purification = Purification.builder()
                .id(1)
                .name(null)
                .sku(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(purification);
        mockMvc.perform(
                put("/purification/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/purification/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}