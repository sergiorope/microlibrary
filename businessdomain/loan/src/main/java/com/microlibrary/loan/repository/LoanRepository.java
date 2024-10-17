package com.microlibrary.loan.repository;

import com.microlibrary.loan.entities.Loan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sergio
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {
    

    @Query("SELECT l.start_date FROM Loan l WHERE l.customer_Id = :customer_Id") 
    List<String> findByCustomerId(@Param("customer_Id") Long customer_Id);
}
