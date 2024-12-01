package com.microlibrary.partner;
import com.microlibrary.partner.dto.PartnerRequest;
import com.microlibrary.partner.dto.PartnerResponse;
import com.microlibrary.partner.entities.Partner;
import com.microlibrary.partner.exception.BussinesRuleException;
import com.microlibrary.partner.repository.PartnerRepository;
import com.microlibrary.partner.service.PartnerService; // Importa el servicio
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

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

class PartnerApplicationTests {

    @MockBean
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerService partnerService;

    @Test
    void findAllTest() throws BussinesRuleException {

        //Given
        List<Partner> partnerListMock = new ArrayList<>();

        partnerListMock.add(new Partner(1L, "Low", "Low Group"));
        partnerListMock.add(new Partner(2L, "Premium", "Premium Group"));

        when(partnerRepository.findAll()).thenReturn(partnerListMock);

        //When
        List<PartnerResponse> partnerList = partnerService.getAll();

        //Then
        assertNotNull(partnerList);
        assertEquals(partnerListMock.size(), partnerList.size());
        assertEquals(partnerListMock.get(0).getName(), partnerList.get(0).getName());

        verify(partnerRepository, times(1)).findAll();
    }

    @Test
    void getByIdTest() throws BussinesRuleException {

        //Given
        Partner partnerMock = new Partner(1L, "Low", "Low Group");

        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partnerMock));

        when(partnerRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        PartnerResponse partner = partnerService.getById(1L);

        //Then
        assertEquals(partnerMock.getName(), partner.getName());
        assertThrows(BussinesRuleException.class, () -> partnerService.getById(2L));
        verify(partnerRepository, times(1)).findById(1L);

    }

    @Test
    void postTest() throws BussinesRuleException {

        //Given
        PartnerRequest partnerRequest = new PartnerRequest("Low", "Low Group");

        Partner partnerMock = new Partner(1L, "Low", "Low Group");

        when(partnerRepository.save(any(Partner.class))).thenReturn(partnerMock);

        //When
        PartnerResponse partnerResponse = partnerService.post(partnerRequest);

        //Then
        assertNotNull(partnerResponse);

        assertEquals("Low", partnerResponse.getName());

        verify(partnerRepository, times(1)).save(any(Partner.class));

    }

    @Test
    void putTest() throws BussinesRuleException {

        //Given
        PartnerRequest partnerRequest = new PartnerRequest("Low", "Low Group");

        Partner partnerMock = new Partner(1L, "Low", "Low Group");

        when(partnerRepository.save(any(Partner.class))).thenReturn(partnerMock);
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partnerMock));
        when(partnerRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        PartnerResponse partnerResponse = partnerService.put(1L, partnerRequest);

        //Then
        assertNotNull(partnerResponse);

        assertEquals("Low", partnerResponse.getName());

        verify(partnerRepository, times(1)).save(any(Partner.class));
        assertThrows(BussinesRuleException.class, () -> partnerService.getById(2L));
        verify(partnerRepository, times(1)).findById(1L);

    }

    @Test
    void putDelete() throws BussinesRuleException {

        //Given
        Partner partnerMock = new Partner(1L, "Low", "Low Group");

        doNothing().when(partnerRepository).delete(any(Partner.class));
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partnerMock));
        when(partnerRepository.findById(2L)).thenReturn(Optional.empty());

        //When
        PartnerResponse partnerResponse = partnerService.delete(1L);

        //Then
        assertNotNull(partnerResponse);

        assertEquals("Low", partnerResponse.getName());

        verify(partnerRepository, times(1)).delete(any(Partner.class));
        assertThrows(BussinesRuleException.class, () -> partnerService.getById(2L));
        verify(partnerRepository, times(1)).findById(1L);

    }

}
