package com.microlibrary.loanline;

import com.microlibrary.loanline.dto.LoanlineRequest;
import com.microlibrary.loanline.dto.LoanlineResponse;
import com.microlibrary.loanline.entities.Loanline;
import com.microlibrary.loanline.exception.BussinesRuleException;
import com.microlibrary.loanline.repository.LoanlineRepository;
import com.microlibrary.loanline.service.LoanlineService; // Importa el servicio
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
class LoanlineApplicationTests {

    @MockBean
    private LoanlineRepository loanlineRepository;

    @Autowired
    private LoanlineService loanlineService;

    @Test
    void findAllTest() throws BussinesRuleException {

        //Given
        List<Loanline> loanlineListMock = new ArrayList<>();

        loanlineListMock.add(new Loanline(1L, 101L, 1001L));
        loanlineListMock.add(new Loanline(2L, 102L, 1002L));

        when(loanlineRepository.findAll()).thenReturn(loanlineListMock);

        //When
        List<LoanlineResponse> loanlineList = loanlineService.getAll();

        //Then
        assertNotNull(loanlineList);
        assertEquals(loanlineListMock.size(), loanlineList.size());
        assertEquals(loanlineListMock.get(0).getProduct_Id(), loanlineList.get(0).getProduct_Id());

        verify(loanlineRepository, times(1)).findAll();
    }

    @Test
    void getByIdTest() throws BussinesRuleException {

        //Given
        Loanline loanlineMock = new Loanline(1L, 101L, 1001L);

        when(loanlineRepository.findById(1L)).thenReturn(Optional.of(loanlineMock));
        when(loanlineRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        LoanlineResponse loanline = loanlineService.getById(1L);

        //Then
        assertEquals(loanlineMock.getProduct_Id(), loanline.getProduct_Id());
        assertThrows(BussinesRuleException.class, () -> loanlineService.getById(2L));
        verify(loanlineRepository, times(1)).findById(1L);

    }

    @Test
    void postTest() throws BussinesRuleException {

        //Given
        LoanlineRequest loanlineRequest = new LoanlineRequest(101L, 1001L);
        Loanline loanlineMock = new Loanline(1L, 101L, 1001L);

        when(loanlineRepository.save(any(Loanline.class))).thenReturn(loanlineMock);

        //When
        LoanlineResponse loanlineResponse = loanlineService.post(loanlineRequest);

        //Then
        assertNotNull(loanlineResponse);
        assertEquals(101L, loanlineResponse.getProduct_Id());

        verify(loanlineRepository, times(1)).save(any(Loanline.class));

    }

    @Test
    void putTest() throws BussinesRuleException {

        //Given
        LoanlineRequest loanlineRequest = new LoanlineRequest(102L, 1002L);
        Loanline loanlineMock = new Loanline(1L, 101L, 1001L);

        when(loanlineRepository.save(any(Loanline.class))).thenReturn(loanlineMock);
        when(loanlineRepository.findById(1L)).thenReturn(Optional.of(loanlineMock));
        when(loanlineRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        LoanlineResponse loanlineResponse = loanlineService.put(1L, loanlineRequest);

        //Then
        assertNotNull(loanlineResponse);
        assertEquals(102L, loanlineResponse.getProduct_Id());

        verify(loanlineRepository, times(1)).save(any(Loanline.class));
        assertThrows(BussinesRuleException.class, () -> loanlineService.getById(2L));
        verify(loanlineRepository, times(1)).findById(1L);

    }

    @Test
    void deleteTest() throws BussinesRuleException {

        //Given
        Loanline loanlineMock = new Loanline(1L, 101L, 1001L);

        doNothing().when(loanlineRepository).delete(any(Loanline.class));
        when(loanlineRepository.findById(1L)).thenReturn(Optional.of(loanlineMock));
        when(loanlineRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        LoanlineResponse loanlineResponse = loanlineService.delete(1L);

        //Then
        assertNotNull(loanlineResponse);
        assertEquals(101L, loanlineResponse.getProduct_Id());

        verify(loanlineRepository, times(1)).delete(any(Loanline.class));
        assertThrows(BussinesRuleException.class, () -> loanlineService.getById(2L));
        verify(loanlineRepository, times(1)).findById(1L);

    }
}
