/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.loan.repository;

import com.microlibrary.loan.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sergio
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
}
