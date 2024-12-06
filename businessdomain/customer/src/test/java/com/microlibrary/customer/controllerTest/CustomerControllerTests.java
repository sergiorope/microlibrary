package com.microlibrary.customer.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.customer.controller.CustomerController;
import com.microlibrary.customer.dto.CustomerRequest;
import com.microlibrary.customer.dto.CustomerResponse;
import com.microlibrary.customer.service.CustomerService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllCustomersTest() throws Exception {
        List<CustomerResponse> customerListMock = new ArrayList<>();
        customerListMock.add(new CustomerResponse(1L, "John", "Doe", 101L));
        customerListMock.add(new CustomerResponse(2L, "Jane", "Smith", 102L));

        when(customerService.getAll()).thenReturn(customerListMock);

        mvc.perform(get("/customer/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"))
                .andExpect(jsonPath("$[0].partner_Id").value(101L));

        verify(customerService).getAll();
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        CustomerResponse customer = new CustomerResponse(1L, "John", "Doe", 101L);

        when(customerService.getById(1L)).thenReturn(customer);

        mvc.perform(get("/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.partner_Id").value(101L));

        verify(customerService).getById(1L);
    }
    
    @Test
    void getByPartnerIdTest() throws Exception {

        List<CustomerResponse> customerListMock = new ArrayList<>();
        customerListMock.add(new CustomerResponse(1L, "John", "Doe", 2L));
        customerListMock.add(new CustomerResponse(2L, "Jane", "Smith", 2L));

        when(customerService.getByPartnerId(2L)).thenReturn(customerListMock);

        mvc.perform(get("/customer/by-partner/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].surname").value("Doe"))
                .andExpect(jsonPath("$[1].surname").value("Smith"));

        verify(customerService).getByPartnerId(2L);
    }

    @Test
    void getPartnerNameTest() throws Exception {
   
        when(customerService.getPartner(1L)).thenReturn("Low");

        mvc.perform(get("/customer/partner/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Low"));  

    
        verify(customerService).getPartner(1L);

    }
    @Test
    void postCustomerTest() throws Exception {
        CustomerRequest request = new CustomerRequest("John", "Doe", 101L);
        CustomerResponse response = new CustomerResponse(1L, "John", "Doe", 101L);

        when(customerService.post(any(CustomerRequest.class))).thenReturn(response);

        mvc.perform(post("/customer/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.partner_Id").value(101L));

        verify(customerService).post(any(CustomerRequest.class));
    }

    @Test
    void putCustomerTest() throws Exception {
        CustomerRequest request = new CustomerRequest("John", "Doe", 101L);
        CustomerResponse response = new CustomerResponse(1L, "John", "Doe", 101L);

        when(customerService.put(eq(1L), any(CustomerRequest.class))).thenReturn(response);

        mvc.perform(put("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.partner_Id").value(101L));

        verify(customerService).put(eq(1L), any(CustomerRequest.class));
    }

    @Test
    void deleteCustomerTest() throws Exception {
        CustomerResponse customerMock = new CustomerResponse(1L, "John", "Doe", 101L);
        when(customerService.delete(1L)).thenReturn(customerMock);

        mvc.perform(delete("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(1L)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.surname").value("Doe"));

        verify(customerService).delete(1L);
    }
}
