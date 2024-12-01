package com.microlibrary.loan;

import com.microlibrary.loan.common.LoanRequestMapper;
import com.microlibrary.loan.common.LoanResponseMapper;
import com.microlibrary.loan.dto.LoanRequest;
import com.microlibrary.loan.dto.LoanResponse;
import com.microlibrary.loan.entities.Loan;
import com.microlibrary.loan.exception.BussinesRuleException;
import com.microlibrary.loan.repository.LoanRepository;
import com.microlibrary.loan.service.LoanService; // Importa el servicio
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
class LoanApplicationTests {

    @MockBean
    private LoanRepository loanRepository;

    @Autowired
    private LoanService loanService;

    @Test
    void findAllTest() throws BussinesRuleException {

        // Given
        List<Loan> loanListMock = new ArrayList<>();

        loanListMock.add(new Loan(1L, "2024-01-01", "2024-02-01", "Pendiente", 1L));
        loanListMock.add(new Loan(2L, "2024-03-01", "2024-04-01", "Expirado", 2L));

        when(loanRepository.findAll()).thenReturn(loanListMock);

        // When
        List<LoanResponse> loanList = loanService.getAll();

        // Then
        assertNotNull(loanList);
        assertEquals(loanListMock.size(), loanList.size());
        assertEquals(loanListMock.get(0).getStart_date(), loanList.get(0).getStart_date());

        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void getByIdTest() throws BussinesRuleException {

        // Given
        Loan loanMock = new Loan(1L, "2024-01-01", "2024-02-01", "Pendiente", 1L);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loanMock));
        when(loanRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        LoanResponse loan = loanService.getById(1L);

        // Then
        assertEquals(loanMock.getStart_date(), loan.getStart_date());
        assertThrows(BussinesRuleException.class, () -> loanService.getById(2L));
        verify(loanRepository, times(1)).findById(1L);
    }

    @Test
    void getByCustomerIdTest() throws BussinesRuleException {

        // Given
        List<Loan> loanListMock = new ArrayList<>();

        loanListMock.add(new Loan(1L, "2024-01-01", "2024-02-01", "Pendiente", 1L));
        loanListMock.add(new Loan(2L, "2024-03-01", "2024-04-01", "Expirado", 2L));

        when(loanRepository.findByCustomerId(1L)).thenReturn((loanListMock));

        // When
        List<LoanResponse> loanResponse = loanService.getByCustomerId(1L);

        // Then
        assertEquals("2024-02-01", loanResponse.get(0).getEnd_date());
        assertThrows(BussinesRuleException.class, () -> loanService.getByCustomerId(2L));
        verify(loanRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void postTest() throws BussinesRuleException {

        // Given
        LoanRequest loanRequest = new LoanRequest("2024-01-01", "2024-02-01", "Pendiente", 1L);

        Loan loanMock = new Loan(1L, "2024-01-01", "2024-02-01", "Pendiente", 1L);

        when(loanRepository.save(any(Loan.class))).thenReturn(loanMock);

        // When
        LoanResponse loanResponse = loanService.post(loanRequest);

        // Then
        assertNotNull(loanResponse);
        assertEquals("2024-01-01", loanResponse.getStart_date());

        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void putTest() throws BussinesRuleException {

        // Given
        LoanRequest loanRequest = new LoanRequest("2024-01-01", "2024-02-01", "Pendiente", 1L);

        Loan loanMock = new Loan(1L, "2024-01-01", "2024-02-01", "Pendiente", 1L);

        when(loanRepository.save(any(Loan.class))).thenReturn(loanMock);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loanMock));
        when(loanRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        LoanResponse loanResponse = loanService.put(1L, loanRequest);

        // Then
        assertNotNull(loanResponse);
        assertEquals("2024-01-01", loanResponse.getStart_date());

        verify(loanRepository, times(1)).save(any(Loan.class));
        assertThrows(BussinesRuleException.class, () -> loanService.getById(2L));
        verify(loanRepository, times(1)).findById(1L);
    }

    @Test
    void putDelete() throws BussinesRuleException {

        // Given
        Loan loanMock = new Loan(1L, "2024-01-01", "2024-02-01", "Pendiente", 1L);

        doNothing().when(loanRepository).delete(any(Loan.class));
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loanMock));
        when(loanRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        LoanResponse loanResponse = loanService.delete(1L);

        // Then
        assertNotNull(loanResponse);
        assertEquals("2024-01-01", loanResponse.getStart_date());

        verify(loanRepository, times(1)).delete(any(Loan.class));
        assertThrows(BussinesRuleException.class, () -> loanService.getById(2L));
        verify(loanRepository, times(1)).findById(1L);
    }
}
