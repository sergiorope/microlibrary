package com.microlibrary.product;

import com.microlibrary.product.common.ProductRequestMapper;
import com.microlibrary.product.common.ProductResponseMapper;
import com.microlibrary.product.dto.ProductRequest;
import com.microlibrary.product.dto.ProductResponse;
import com.microlibrary.product.entities.Product;
import com.microlibrary.product.exception.BussinesRuleException;
import com.microlibrary.product.repository.ProductRepository;
import com.microlibrary.product.service.ProductService; // Importa el servicio
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

class ProductApplicationTests {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void findAllTest() throws BussinesRuleException {

        //Given
        List<Product> producListMock = new ArrayList<>();

        producListMock.add(new Product(1L, "ejemplo 1", "descripcion ejemplo 1", "autor ejemplo 1", "imagen ejemplo 1"));
        producListMock.add(new Product(2L, "ejemplo 2", "descripcion ejemplo 2", "autor ejemplo 2", "imagen ejemplo 2"));

        when(productRepository.findAll()).thenReturn(producListMock);

        //When
        List<ProductResponse> producList = productService.getAll();

        //Then
        assertNotNull(producList);
        assertEquals(producListMock.size(), producList.size());
        assertEquals(producListMock.get(0).getName(), producList.get(0).getName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getByIdTest() throws BussinesRuleException {

        //Given
        Product productMock = new Product(1L, "Shogun", "libro de samurais", "Jose Ferrer", "https://m.media-amazon.com/images/I/813FAu+JUwL._SL1500_.jpg");

        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));

        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        ProductResponse product = productService.getById(1L);

        //Then
        assertEquals(productMock.getName(), product.getName());
        assertThrows(BussinesRuleException.class, () -> productService.getById(2L));
        verify(productRepository, times(1)).findById(1L);

    }

    @Test
    void postTest() throws BussinesRuleException {

        //Given
        ProductRequest productRequest = new ProductRequest("Shogun", "https://m.media-amazon.com/images/I/813FAu+JUwL._SL1500_.jpg", "libro de samurais", "Jose Ferrer");

        Product productMock = new Product(1L, "Shogun", "libro de samurais", "Jose Ferrer", "https://m.media-amazon.com/images/I/813FAu+JUwL._SL1500_.jpg");

        when(productRepository.save(any(Product.class))).thenReturn(productMock);

        //When
        ProductResponse productResponse = productService.post(productRequest);

        //Then
        assertNotNull(productResponse);

        assertEquals("Shogun", productResponse.getName());

        verify(productRepository, times(1)).save(any(Product.class));

    }

    @Test
    void putTest() throws BussinesRuleException {

        //Given
        ProductRequest productRequest = new ProductRequest("Shogun", "https://m.media-amazon.com/images/I/813FAu+JUwL._SL1500_.jpg", "libro de samurais", "Jose Ferrer");

        Product productMock = new Product(1L, "Shogun", "libro de samurais", "Jose Ferrer", "https://m.media-amazon.com/images/I/813FAu+JUwL._SL1500_.jpg");

        when(productRepository.save(any(Product.class))).thenReturn(productMock);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        ProductResponse productResponse = productService.put(1L, productRequest);

        //Then
        assertNotNull(productResponse);

        assertEquals("Shogun", productResponse.getName());

        verify(productRepository, times(1)).save(any(Product.class));
        assertThrows(BussinesRuleException.class, () -> productService.getById(2L));
        verify(productRepository, times(1)).findById(1L);

    }

    @Test
    void putDelete() throws BussinesRuleException {

        //Given

        Product productMock = new Product(1L, "Shogun", "libro de samurais", "Jose Ferrer", "https://m.media-amazon.com/images/I/813FAu+JUwL._SL1500_.jpg");

        doNothing().when(productRepository).delete(any(Product.class));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        ProductResponse productResponse = productService.delete(1L);

        //Then
        assertNotNull(productResponse);

        assertEquals("Shogun", productResponse.getName());

        verify(productRepository, times(1)).delete(any(Product.class));
        assertThrows(BussinesRuleException.class, () -> productService.getById(2L));
        verify(productRepository, times(1)).findById(1L);

    }

}
