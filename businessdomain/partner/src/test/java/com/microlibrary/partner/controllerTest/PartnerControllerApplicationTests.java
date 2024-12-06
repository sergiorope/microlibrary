package com.microlibrary.partner.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.partner.controller.PartnerController;
import com.microlibrary.partner.dto.PartnerRequest;
import com.microlibrary.partner.dto.PartnerResponse;
import com.microlibrary.partner.service.PartnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(PartnerController.class)
public class PartnerControllerApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PartnerService partnerService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllTest() throws Exception {
        List<PartnerResponse> partnerListMock = new ArrayList<>();
        partnerListMock.add(new PartnerResponse(1L, "Partner One", "Description of Partner One"));
        partnerListMock.add(new PartnerResponse(2L, "Partner Two", "Description of Partner Two"));

        when(partnerService.getAll()).thenReturn(partnerListMock);

        mvc.perform(get("/partner/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Partner One"))
                .andExpect(jsonPath("$[0].description").value("Description of Partner One"));

        verify(partnerService).getAll();
    }

    @Test
    void getByIdTest() throws Exception {
        PartnerResponse partnerMock = new PartnerResponse(1L, "Partner One", "Description of Partner One");

        when(partnerService.getById(1L)).thenReturn(partnerMock);

        mvc.perform(get("/partner/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Partner One"))
                .andExpect(jsonPath("$.description").value("Description of Partner One"));

        verify(partnerService).getById(1L);
    }

    @Test
    void postTest() throws Exception {
        PartnerResponse partnerMock = new PartnerResponse(1L, "Partner One", "Description of Partner One");
        PartnerRequest partner = new PartnerRequest("Partner One", "Description of Partner One");

        when(partnerService.post(any(PartnerRequest.class))).thenReturn(partnerMock);

        

        mvc.perform(post("/partner/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partner)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Partner One"));

    }

    @Test
    void putTest() throws Exception {
        PartnerResponse partnerMock = new PartnerResponse(1L, "Updated Partner", "Updated description");
        PartnerRequest partner = new PartnerRequest("Updated Partner", "Updated description");

        when(partnerService.put(eq(1L), any(PartnerRequest.class))).thenReturn(partnerMock);

        mvc.perform(put("/partner/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partner)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Partner"));

        verify(partnerService).put(eq(1L), any(PartnerRequest.class));
    }

    @Test
    void deleteTest() throws Exception {
        PartnerResponse partnerMock = new PartnerResponse(1L, "Partner One", "Description of Partner One");

        when(partnerService.delete(1L)).thenReturn(partnerMock);

        mvc.perform(delete("/partner/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Partner One"));

        verify(partnerService).delete(1L);
    }
}
