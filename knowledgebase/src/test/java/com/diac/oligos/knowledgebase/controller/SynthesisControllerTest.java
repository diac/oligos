package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.enumeration.BaseType;
import com.diac.oligos.domain.model.Synthesis;
import com.diac.oligos.knowledgebase.service.SynthesisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SynthesisController.class)
@AutoConfigureMockMvc
public class SynthesisControllerTest {

    private static final String URL_BASE = "/synthesis";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SynthesisService synthesisService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(synthesisService.findById(id)).thenReturn(Synthesis.builder().id(id).build());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(synthesisService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(synthesisService.findBySku(sku))
                .thenReturn(
                        Synthesis.builder()
                                .id(1)
                                .sku(sku)
                                .build()
                );
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenFindByNotSkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(synthesisService.findBySku(sku)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/find_by_sku/%s", URL_BASE, sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis newSynthesis = Synthesis.builder()
                .name(value)
                .sku(value)
                .baseType(BaseType.DNA)
                .build();
        Synthesis savedSynthesis = Synthesis.builder()
                .id(id)
                .name(value)
                .sku(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newSynthesis);
        String responseBody = objectWriter.writeValueAsString(savedSynthesis);
        Mockito.when(synthesisService.add(newSynthesis)).thenReturn(savedSynthesis);
        mockMvc.perform(
                        post(URL_BASE)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        Synthesis synthesis = Synthesis.builder()
                .name(null)
                .sku(null)
                .baseType(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(synthesis);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Synthesis synthesis = Synthesis.builder()
                .name("")
                .sku("")
                .baseType(BaseType.DNA)
                .build();
        String requestBody = objectWriter.writeValueAsString(synthesis);
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
        Synthesis synthesis = Synthesis.builder()
                .id(id)
                .name(value)
                .sku(value)
                .baseType(BaseType.DNA)
                .build();
        String jsonValue = objectWriter.writeValueAsString(synthesis);
        Mockito.when(synthesisService.update(id, synthesis)).thenReturn(synthesis);
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
        Synthesis synthesis = Synthesis.builder()
                .id(id)
                .name(null)
                .sku(null)
                .baseType(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(synthesis);
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
        Synthesis synthesis = Synthesis.builder()
                .id(id)
                .name("")
                .sku("")
                .baseType(BaseType.DNA)
                .build();
        String requestBody = objectWriter.writeValueAsString(synthesis);
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