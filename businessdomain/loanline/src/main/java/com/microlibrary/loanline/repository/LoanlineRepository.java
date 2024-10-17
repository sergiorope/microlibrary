/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.loanline.repository;

import com.microlibrary.loanline.entities.Loanline;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sergio
 */
public interface LoanlineRepository extends JpaRepository<Loanline, Long> {
    
     @Query("SELECT li FROM Loanline li WHERE li.loan_Id = :loan_Id") 
    List<Loanline> findByLoanId(@Param("loan_Id") Long loan_Id);

}
