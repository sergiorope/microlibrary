package com.microlibrary.loan.jpaRepositoryTest;

import com.microlibrary.loan.entities.Loan;
import com.microlibrary.loan.repository.LoanRepository;

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

public class LoanJpaRepositoryApplicationTests {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void findByCustomerIdTest() {

        Loan loan1 = new Loan(1L, "19-05-2024", "19-05-2024", "Pendiente", 2L);
        Loan loan2 = new Loan(2L, "12-05-2023", "12-05-2024", "Expirado", 2L);
        Loan loan3 = new Loan(3L, "10-07-2023", "17-07-2023", "Expirado", 2L);

        loanRepository.saveAll(List.of(loan1, loan2, loan3));

        List<Loan> result = loanRepository.findByCustomerId(2L);

        assertEquals(3, result.size());
    }
}
