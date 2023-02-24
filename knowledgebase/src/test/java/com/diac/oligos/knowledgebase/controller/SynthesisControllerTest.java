package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Synthesis;
import com.diac.oligos.knowledgebase.service.SynthesisService;
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
        Mockito.when(synthesisService.findById(id)).thenReturn(Optional.of(Synthesis.builder().id(id).build()));
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(synthesisService.findById(id)).thenReturn(Optional.empty());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
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
    public void whenPut() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Synthesis synthesis = Synthesis.builder()
                .id(id)
                .name(value)
                .sku(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(synthesis);
        Mockito.when(synthesisService.update(synthesis)).thenReturn(synthesis);
        mockMvc.perform(
                        put(URL_BASE)
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(jsonValue));
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}