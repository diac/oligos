package com.diac.oligos.knowledgebase.controller;

import com.diac.oligos.domain.model.Modification;
import com.diac.oligos.knowledgebase.service.ModificationService;
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

@WebMvcTest(ModificationController.class)
@AutoConfigureMockMvc
public class ModificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModificationService modificationService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/modification")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(modificationService.findById(id)).thenReturn(Optional.of(Modification.builder().id(id).build()));
        String requestUrl = String.format("/modification/%d", id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(modificationService.findById(id)).thenReturn(Optional.empty());
        String requestUrl = String.format("/modification/%d", id);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(modificationService.findBySku(sku))
                .thenReturn(
                        Optional.of(Modification.builder()
                                .id(1)
                                .sku(sku)
                                .build())
                );
        String requestUrl = String.format("/modification/find_by_sku/%s", sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }


    @Test
    public void whenFindBySkuNotFound() throws Exception {
        String sku = "qwerty";
        Mockito.when(modificationService.findBySku(sku)).thenReturn(Optional.empty());
        String requestUrl = String.format("/modification/find_by_sku/%s", sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Modification newModification = Modification.builder()
                .name(value)
                .sku(value)
                .code(value)
                .build();
        Modification savedModification = Modification.builder()
                .id(id)
                .name(value)
                .sku(value)
                .code(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newModification);
        String responseBody = objectWriter.writeValueAsString(savedModification);
        Mockito.when(modificationService.add(newModification)).thenReturn(savedModification);
        mockMvc.perform(
                        post("/modification")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        Modification modification = Modification.builder()
                .name(null)
                .code(null)
                .sku(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(modification);
        mockMvc.perform(
                post("/modification")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Modification modification = Modification.builder()
                .name("")
                .code("")
                .sku("")
                .build();
        String requestBody = objectWriter.writeValueAsString(modification);
        mockMvc.perform(
                post("/modification")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = String.valueOf(System.currentTimeMillis());
        Modification modification = Modification.builder()
                .id(id)
                .name(value)
                .sku(value)
                .code(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(modification);
        Mockito.when(modificationService.update(modification)).thenReturn(modification);
        mockMvc.perform(
                        put("/modification")
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(jsonValue));
    }

    @Test
    public void whenPutWithNullValuesThenStatusIsBadRequest() throws Exception {
        Modification modification = Modification.builder()
                .id(1)
                .name(null)
                .code(null)
                .sku(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(modification);
        mockMvc.perform(
                put("/modification")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBkankValuesThenStatusIsBadRequest() throws Exception {
        Modification modification = Modification.builder()
                .id(1)
                .name(null)
                .code(null)
                .sku(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(modification);
        mockMvc.perform(
                put("/modification")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/modification/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}