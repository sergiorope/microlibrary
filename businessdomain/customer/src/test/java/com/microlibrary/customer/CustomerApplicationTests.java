package com.microlibrary.customer;

import com.microlibrary.customer.common.CustomerRequestMapper;
import com.microlibrary.customer.common.CustomerResponseMapper;
import com.microlibrary.customer.dto.CustomerRequest;
import com.microlibrary.customer.dto.CustomerResponse;
import com.microlibrary.customer.entities.Customer;
import com.microlibrary.customer.exception.BussinesRuleException;
import com.microlibrary.customer.repository.CustomerRepository;
import com.microlibrary.customer.service.CustomerService;
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

class CustomerApplicationTests {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    void findAllTest() throws BussinesRuleException {

        //Given
        List<Customer> customerListMock = new ArrayList<>();

        customerListMock.add(new Customer(1L, "Pepe", "Sanchez", 101L));
        customerListMock.add(new Customer(2L, "Juan", "Perez", 102L));

        when(customerRepository.findAll()).thenReturn(customerListMock);

        //When
        List<CustomerResponse> customerList = customerService.getAll();

        //Then
        assertNotNull(customerList);
        assertEquals(customerListMock.size(), customerList.size());
        assertEquals(customerListMock.get(0).getName(), customerList.get(0).getName());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getByIdTest() throws BussinesRuleException {

        //Given
        Customer customerMock = new Customer(1L, "Pepe", "Sanchez", 101L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerMock));

        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        CustomerResponse customer = customerService.getById(1L);

        //Then
        assertEquals(customerMock.getName(), customer.getName());
        assertThrows(BussinesRuleException.class, () -> customerService.getById(2L));
        verify(customerRepository, times(1)).findById(1L);

    }

    @Test
    void postTest() throws BussinesRuleException {

        //Given
        CustomerRequest customerRequest = new CustomerRequest("Pepe", "Sanchez", 101L);

        Customer customerMock = new Customer(1L, "Pepe", "Sanchez", 101L);

        when(customerRepository.save(any(Customer.class))).thenReturn(customerMock);

        //When
        CustomerResponse customerResponse = customerService.post(customerRequest);

        //Then
        assertNotNull(customerResponse);

        assertEquals("Pepe", customerResponse.getName());

        verify(customerRepository, times(1)).save(any(Customer.class));

    }

    @Test
    void putTest() throws BussinesRuleException {

        //Given
        CustomerRequest customerRequest = new CustomerRequest("Pepe", "Sanchez", 101L);

        Customer customerMock = new Customer(1L, "Pepe", "Sanchez", 101L);

        when(customerRepository.save(any(Customer.class))).thenReturn(customerMock);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerMock));
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        CustomerResponse customerResponse = customerService.put(1L, customerRequest);

        //Then
        assertNotNull(customerResponse);

        assertEquals("Pepe", customerResponse.getName());

        verify(customerRepository, times(1)).save(any(Customer.class));
        assertThrows(BussinesRuleException.class, () -> customerService.getById(2L));
        verify(customerRepository, times(1)).findById(1L);

    }

    @Test
    void putDelete() throws BussinesRuleException {

        //Given

        Customer customerMock = new Customer(1L, "Pepe", "Sanchez", 101L);

        doNothing().when(customerRepository).delete(any(Customer.class));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerMock));
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        CustomerResponse customerResponse = customerService.delete(1L);

        //Then
        assertNotNull(customerResponse);

        assertEquals("Pepe", customerResponse.getName());

        verify(customerRepository, times(1)).delete(any(Customer.class));
        assertThrows(BussinesRuleException.class, () -> customerService.getById(2L));
        verify(customerRepository, times(1)).findById(1L);

    }

}
