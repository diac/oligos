package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Scale;
import com.diac.oligos.knowledgebase.service.ScaleService;
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

@WebMvcTest(ScaleController.class)
@AutoConfigureMockMvc
public class ScaleControllerTest {

    private static final String URL_BASE = "/scale";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ScaleService scaleService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

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
        Mockito.when(scaleService.findById(id)).thenReturn(Scale.builder().id(id).build());
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(scaleService.findById(id)).thenThrow(NoResultException.class);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        int nanomols = 1000;
        String value = String.valueOf(System.currentTimeMillis());
        Scale newScale = Scale.builder()
                .displayName(value)
                .nanomols(nanomols)
                .build();
        Scale savedScale = Scale.builder()
                .id(id)
                .displayName(value)
                .nanomols(nanomols)
                .build();
        String requestBody = objectWriter.writeValueAsString(newScale);
        String responseBody = objectWriter.writeValueAsString(savedScale);
        Mockito.when(scaleService.add(newScale)).thenReturn(savedScale);
        mockMvc.perform(
                        post(URL_BASE)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
            Scale scale = Scale.builder()
                    .displayName(null)
                    .build();
        String requestBody = objectWriter.writeValueAsString(scale);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Scale scale = Scale.builder()
                .displayName("")
                .build();
        String requestBody = objectWriter.writeValueAsString(scale);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithZeroNanomolsThenResponseStatusIsBadRequest() throws Exception {
        Scale scale = Scale.builder()
                .nanomols(0)
                .displayName("ZERO")
                .build();
        String requestBody = objectWriter.writeValueAsString(scale);
        mockMvc.perform(
                post(URL_BASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        int nanomols = 1000;
        String value = String.valueOf(System.currentTimeMillis());
        Scale scale = Scale.builder()
                .id(id)
                .displayName(value)
                .nanomols(nanomols)
                .build();
        String jsonValue = objectWriter.writeValueAsString(scale);
        Mockito.when(scaleService.update(id, scale)).thenReturn(scale);
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
        Scale scale = Scale.builder()
                .id(id)
                .displayName(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(scale);
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
        Scale scale = Scale.builder()
                .id(id)
                .displayName("")
                .build();
        String requestBody = objectWriter.writeValueAsString(scale);
        String requestUrl = String.format("%s/%d", URL_BASE, id);
        mockMvc.perform(
                put(requestUrl)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithZeroNanomolsThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        Scale scale = Scale.builder()
                .id(id)
                .nanomols(0)
                .displayName("ZERO")
                .build();
        String requestBody = objectWriter.writeValueAsString(scale);
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