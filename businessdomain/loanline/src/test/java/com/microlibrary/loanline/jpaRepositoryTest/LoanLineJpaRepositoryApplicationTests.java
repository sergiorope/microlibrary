package com.microlibrary.loanline.jpaRepositoryTest;

import com.microlibrary.loanline.controllerTest.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microlibrary.loanline.controller.LoanlineController;
import com.microlibrary.loanline.dto.LoanlineRequest;
import com.microlibrary.loanline.dto.LoanlineResponse;
import com.microlibrary.loanline.entities.Loanline;
import com.microlibrary.loanline.repository.LoanlineRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testloanlinedb;DB_CLOSE_DELAY=-1",  
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",  
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})

public class LoanLineJpaRepositoryApplicationTests {

    @Autowired
    private LoanlineRepository loanlineRepository;

    @Test
    void findByLoanIdTest() {
        // Preparar datos de prueba
        Loanline loanline1 = new Loanline(1L, 1L, 2L);
        Loanline loanline2 = new Loanline(2L, 2L, 2L);
        Loanline loanline3 = new Loanline(3L, 3L, 2L);

        loanlineRepository.saveAll(List.of(loanline1, loanline2, loanline3));

        List<Loanline> result = loanlineRepository.findByLoanId(2L);

        assertEquals(3, result.size());
    }
}
