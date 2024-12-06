package com.microlibrary.loan.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.loan.controller.LoanController;
import com.microlibrary.loan.dto.LoanRequest;
import com.microlibrary.loan.dto.LoanResponse;
import com.microlibrary.loan.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(LoanController.class)
public class LoanControllerApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoanService loanService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllTest() throws Exception {
        List<LoanResponse> loanListMock = new ArrayList<>();
        loanListMock.add(new LoanResponse(1L, "2023-01-01", "2023-12-31", "Pendiente", 101L));
        loanListMock.add(new LoanResponse(2L, "2022-05-01", "2022-11-30", "Expirado", 102L));

        when(loanService.getAll()).thenReturn(loanListMock);

        mvc.perform(get("/loan/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].start_date").value("2023-01-01"))
                .andExpect(jsonPath("$[0].status").value("Pendiente"));

        verify(loanService).getAll();
    }

    @Test
    void getByIdTest() throws Exception {
        LoanResponse loanMock = new LoanResponse(1L, "2023-01-01", "2023-12-31", "Pendiente", 101L);

        when(loanService.getById(1L)).thenReturn(loanMock);

        mvc.perform(get("/loan/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.start_date").value("2023-01-01"))
                .andExpect(jsonPath("$.customer_Id").value(101L));

        verify(loanService).getById(1L);
    }
    
    @Test
    void getByCustomerIdTest() throws Exception {

        List<LoanResponse> loanListMock = new ArrayList<>();
        loanListMock.add(new LoanResponse(1L, "2023-01-01", "2023-12-31", "Pendiente", 5L));
        loanListMock.add(new LoanResponse(2L, "2022-05-01", "2022-11-30", "Expirado", 5L));

        when(loanService.getByCustomerId(5L)).thenReturn(loanListMock);

        mvc.perform(get("/loan/by-customer/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].start_date").value("2023-01-01"))
                .andExpect(jsonPath("$[1].start_date").value("2022-05-01"));

        verify(loanService).getByCustomerId(5L);
    }
    
    
    @Test
    void getProductNameTest() throws Exception {
   
        when(loanService.getCustomer(1L)).thenReturn("Juan");

        mvc.perform(get("/loan/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Juan"));  

    
        verify(loanService).getCustomer(1L);

    }

    @Test
    void postTest() throws Exception {
        LoanRequest loanRequest = new LoanRequest("2023-01-01", "2023-12-31", "Pendiente", 101L);
        LoanResponse loanResponse = new LoanResponse(1L, "2023-01-01", "2023-12-31", "Pendiente", 101L);

        when(loanService.post(any(LoanRequest.class))).thenReturn(loanResponse);

        mvc.perform(post("/loan/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Pendiente"));

        verify(loanService).post(any(LoanRequest.class));
    }

    @Test
    void putTest() throws Exception {
        LoanRequest loanRequest = new LoanRequest("2023-01-01", "2023-12-31", "Pendiente", 101L);
        LoanResponse loanResponse = new LoanResponse(1L, "2023-01-01", "2023-12-31", "Pendiente", 101L);

        when(loanService.put(eq(1L), any(LoanRequest.class))).thenReturn(loanResponse);

        mvc.perform(put("/loan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customer_Id").value(101L));

        verify(loanService).put(eq(1L), any(LoanRequest.class));
    }

    @Test
    void deleteTest() throws Exception {
        LoanResponse loanResponse = new LoanResponse(1L, "2023-01-01", "2023-12-31", "Pendiente", 101L);

        when(loanService.delete(1L)).thenReturn(loanResponse);

        mvc.perform(delete("/loan/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Pendiente"));

        verify(loanService).delete(1L);
    }
}
