package com.microlibrary.customer.jpaRepositoryTest;

import com.microlibrary.customer.entities.Customer;
import com.microlibrary.customer.repository.CustomerRepository;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testloandb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})

public class CustomerJpaRepositoryApplicationTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findByPartnerIdTest() {

        Customer customer1 = new Customer(1L, "Pepe", "Sanchez", 3L);
         Customer customer2 = new Customer(2L, "Juan", "Domingo", 3L);
          Customer customer3 = new Customer(3L, "Pedro", "Paez", 3L);

        customerRepository.saveAll(List.of(customer1, customer2, customer3));

        List<Customer> result = customerRepository.findByPartnerId(3L);

        assertEquals(3, result.size());
    }
}
