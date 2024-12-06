package com.microlibrary.loanline.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.loanline.controller.LoanlineController;
import com.microlibrary.loanline.dto.LoanlineRequest;
import com.microlibrary.loanline.dto.LoanlineResponse;
import com.microlibrary.loanline.service.LoanlineService;
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

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(LoanlineController.class)
public class LoanLineControllerApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoanlineService loanLineService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllTest() throws Exception {

        List<LoanlineResponse> loanLineListMock = new ArrayList<>();
        loanLineListMock.add(new LoanlineResponse(1L, 101L, 1001L));
        loanLineListMock.add(new LoanlineResponse(2L, 102L, 1002L));

        when(loanLineService.getAll()).thenReturn(loanLineListMock);

        mvc.perform(get("/loanline/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].product_Id").value(101L))
                .andExpect(jsonPath("$[0].loan_Id").value(1001L));

        verify(loanLineService).getAll();
    }

    @Test
    void getByIdTest() throws Exception {

        LoanlineResponse loanLineMock = new LoanlineResponse(1L, 101L, 1001L);

        when(loanLineService.getById(1L)).thenReturn(loanLineMock);

        mvc.perform(get("/loanline/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.product_Id").value(101L))
                .andExpect(jsonPath("$.loan_Id").value(1001L));

        verify(loanLineService).getById(1L);
    }

    @Test
    void getByLoanIdTest() throws Exception {

        List<LoanlineResponse> loanLineListMock = new ArrayList<>();
        loanLineListMock.add(new LoanlineResponse(2L, 102L, 1002L));
        loanLineListMock.add(new LoanlineResponse(3L, 103L, 1002L));

        when(loanLineService.getByLoanId(1002L)).thenReturn(loanLineListMock);

        mvc.perform(get("/loanline/by-loan/1002").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].product_Id").value(102L))
                .andExpect(jsonPath("$[1].product_Id").value(103L));

        verify(loanLineService).getByLoanId(1002L);
    }

    @Test
    void getProductNameTest() throws Exception {
   
        when(loanLineService.getProduct(1L)).thenReturn("La isla de la mujer dormida");

        mvc.perform(get("/loanline/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("La isla de la mujer dormida"));  

    
        verify(loanLineService).getProduct(1L);

    }

    @Test
    void postTest() throws Exception {

        LoanlineResponse loanLineMock = new LoanlineResponse(1L, 101L, 1001L);
        LoanlineRequest loanLine = new LoanlineRequest(101L, 1001L);

        when(loanLineService.post(any(LoanlineRequest.class))).thenReturn(loanLineMock);

        mvc.perform(post("/loanline/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loanLine)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.product_Id").value(101L))
                .andExpect(jsonPath("$.loan_Id").value(1001L));

        verify(loanLineService).post(any(LoanlineRequest.class));
    }

    @Test
    void putTest() throws Exception {

        LoanlineResponse loanLineMock = new LoanlineResponse(1L, 101L, 1001L);
        LoanlineRequest loanLine = new LoanlineRequest(101L, 1001L);

        when(loanLineService.put(eq(1L), any(LoanlineRequest.class))).thenReturn(loanLineMock);

        mvc.perform(put("/loanline/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loanLine)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.product_Id").value(101L))
                .andExpect(jsonPath("$.loan_Id").value(1001L));

        verify(loanLineService).put(eq(1L), any(LoanlineRequest.class));
    }

    @Test
    void deleteTest() throws Exception {

        LoanlineResponse loanLineMock = new LoanlineResponse(1L, 101L, 1001L);
        when(loanLineService.delete(1L)).thenReturn(loanLineMock);

        mvc.perform(delete("/loanline/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(1L)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.product_Id").value(101L))
                .andExpect(jsonPath("$.loan_Id").value(1001L));

        verify(loanLineService).delete(1L);
    }
}
