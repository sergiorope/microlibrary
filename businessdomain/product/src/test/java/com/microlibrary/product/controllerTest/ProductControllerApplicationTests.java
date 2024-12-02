package com.microlibrary.product.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.product.controller.ProductController;
import com.microlibrary.product.dto.ProductResponse;
import com.microlibrary.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ProductController.class)
public class ProductControllerApplicationTests {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;
    
    ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }
     
    @Test
    void getAllTest() throws Exception {
        
        List<ProductResponse> producListMock = new ArrayList<>();
        producListMock.add(new ProductResponse(1L, "ejemplo 1", "descripcion ejemplo 1", "autor ejemplo 1", "imagen ejemplo 1"));
        producListMock.add(new ProductResponse(2L, "ejemplo 2", "descripcion ejemplo 2", "autor ejemplo 2", "imagen ejemplo 2"));

        when(productService.getAll()).thenReturn(producListMock);

        mvc.perform(get("/product/list").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name").value("ejemplo 1")) 
            .andExpect(jsonPath("$[0].autor").value("autor ejemplo 1"));

        verify(productService).getAll();
    }
    
    @Test
    void getByIdTest() throws Exception {
        
        ProductResponse productMock = new ProductResponse(1L, "ejemplo 1", "descripcion ejemplo 1", "autor ejemplo 1", "imagen ejemplo 1");

        when(productService.getById(1L)).thenReturn(productMock);

        mvc.perform(get("/product/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.image").value("imagen ejemplo 1"));

        verify(productService).getById(1L);
    }
}