package com.microlibrary.product.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.product.controller.ProductController;
import com.microlibrary.product.dto.ProductRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

        List<ProductResponse> productListMock = new ArrayList<>();
        productListMock.add(new ProductResponse(1L, "The Great Gatsby", "A novel written by F. Scott Fitzgerald, set in the Roaring Twenties.", "F. Scott Fitzgerald", "https://imagessl0.casadellibro.com/a/l/s7/40/9781847496140.webp"));
        productListMock.add(new ProductResponse(2L, "1984", "A dystopian novel by George Orwell about totalitarianism and surveillance.", "George Orwell", "https://imagessl4.casadellibro.com/a/l/s7/44/9788499890944.webp"));

        when(productService.getAll()).thenReturn(productListMock);

        mvc.perform(get("/product/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("The Great Gatsby"))
                .andExpect(jsonPath("$[0].autor").value("F. Scott Fitzgerald"));

        verify(productService).getAll();
    }

    @Test
    void getByIdTest() throws Exception {

        ProductResponse productMock = new ProductResponse(1L, "The Great Gatsby", "A novel written by F. Scott Fitzgerald, set in the Roaring Twenties.", "F. Scott Fitzgerald", "https://imagessl0.casadellibro.com/a/l/s7/40/9781847496140.webp");

        when(productService.getById(1L)).thenReturn(productMock);

        mvc.perform(get("/product/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.image").value("https://imagessl0.casadellibro.com/a/l/s7/40/9781847496140.webp"));

        verify(productService).getById(1L);
    }

    @Test
    void postTest() throws Exception {

        ProductResponse productMock = new ProductResponse(1L, "The Great Gatsby", "A novel written by F. Scott Fitzgerald, set in the Roaring Twenties.", "F. Scott Fitzgerald", "https://imagessl0.casadellibro.com/a/l/s7/40/9781847496140.webp");
        ProductRequest product = new ProductRequest("The Great Gatsby", "A novel written by F. Scott Fitzgerald, set in the Roaring Twenties.", "F. Scott Fitzgerald", "https://imagessl0.casadellibro.com/a/l/s7/40/9781847496140.webp");

        when(productService.post(any(ProductRequest.class))).thenReturn(productMock);

        mvc.perform(post("/product/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("The Great Gatsby"));

        verify(productService).post(any(ProductRequest.class));

    }

    @Test
    void putTest() throws Exception {

        ProductResponse productMock = new ProductResponse(1L, "The Great Gatsby", "A novel written by F. Scott Fitzgerald, set in the Roaring Twenties.", "F. Scott Fitzgerald", "https://imagessl0.casadellibro.com/a/l/s7/40/9781847496140.webp");
        ProductRequest product = new ProductRequest("The Great Gatsby", "A novel written by F. Scott Fitzgerald, set in the Roaring Twenties.", "F. Scott Fitzgerald", "https://imagessl0.casadellibro.com/a/l/s7/40/9781847496140.webp");

        when(productService.put(eq(1L), any(ProductRequest.class))).thenReturn(productMock);

        mvc.perform(put("/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("The Great Gatsby"));

        verify(productService).put(eq(1L), any(ProductRequest.class));

    }

    @Test
    void deleteTest() throws Exception {

        ProductResponse productMock = new ProductResponse(1L, "The Great Gatsby", "A novel written by F. Scott Fitzgerald, set in the Roaring Twenties.", "F. Scott Fitzgerald", "https://imagessl0.casadellibro.com/a/l/s7/40/9781847496140.webp");
        when(productService.delete(1L)).thenReturn(productMock);

        mvc.perform(delete("/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(1L)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("The Great Gatsby"));

        verify(productService).delete(1L);

    }
    
    

}
