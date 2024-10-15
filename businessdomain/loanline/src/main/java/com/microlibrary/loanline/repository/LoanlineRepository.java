/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.loanline.repository;

import com.microlibrary.loanline.entities.Loanline;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sergio
 */
public interface LoanlineRepository extends JpaRepository<Loanline, Long> {

}
