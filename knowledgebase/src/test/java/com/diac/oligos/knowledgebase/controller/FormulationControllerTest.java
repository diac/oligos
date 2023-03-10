package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Formulation;
import com.diac.oligos.knowledgebase.service.FormulationService;
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

@WebMvcTest(FormulationController.class)
@AutoConfigureMockMvc
public class FormulationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private FormulationService formulationService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/formulation")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(formulationService.findById(id)).thenReturn(Formulation.builder().id(id).build());
        String requestUrl = String.format("/formulation/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(formulationService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("/formulation/%d", id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(formulationService.findBySku(sku))
                .thenReturn(
                        Formulation.builder()
                                .id(1)
                                .sku(sku)
                                .build()
                );
        String requestUrl = String.format("/formulation/find_by_sku/%s", sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenFindBySkuNotFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(formulationService.findBySku(sku)).thenThrow(NoResultException.class);
        String requestUrl = String.format("/formulation/find_by_sku/%s", sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Formulation newFormulation = Formulation.builder()
                .name(value)
                .sku(value)
                .available(true)
                .build();
        Formulation savedFormulation = Formulation.builder()
                .id(id)
                .name(value)
                .sku(value)
                .available(true)
                .build();
        String requestBody = objectWriter.writeValueAsString(newFormulation);
        String responseBody = objectWriter.writeValueAsString(savedFormulation);
        Mockito.when(formulationService.add(newFormulation)).thenReturn(savedFormulation);
        mockMvc.perform(
                        post("/formulation")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        Formulation formulation = Formulation.builder()
                .name(null)
                .sku(null)
                .available(true)
                .build();
        String requestBody = objectWriter.writeValueAsString(formulation);
        mockMvc.perform(
                post("/formulation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Formulation formulation = Formulation.builder()
                .name("")
                .sku("")
                .available(true)
                .build();
        String requestBody = objectWriter.writeValueAsString(formulation);
        mockMvc.perform(
                post("/formulation")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Formulation formulation = Formulation.builder()
                .id(id)
                .name(value)
                .sku(value)
                .available(true)
                .build();
        String jsonValue = objectWriter.writeValueAsString(formulation);
        Mockito.when(formulationService.update(id, formulation)).thenReturn(formulation);
        mockMvc.perform(
                        put("/formulation/1")
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(jsonValue));
    }

    @Test
    public void whenPutWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        Formulation formulation = Formulation.builder()
                .name(null)
                .sku(null)
                .available(true)
                .build();
        String requestBody = objectWriter.writeValueAsString(formulation);
        mockMvc.perform(
                put("/formulation/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Formulation formulation = Formulation.builder()
                .name("")
                .sku("")
                .available(true)
                .build();
        String requestBody = objectWriter.writeValueAsString(formulation);
        mockMvc.perform(
                put("/formulation/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/formulation/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}