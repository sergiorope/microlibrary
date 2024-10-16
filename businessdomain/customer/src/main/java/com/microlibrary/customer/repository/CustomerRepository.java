/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.customer.repository;

import com.microlibrary.customer.entities.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sergio
 */



public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query("SELECT c.name FROM Customer c WHERE c.partner_Id = :partner_Id")
    List<Customer> findByPartnerId(@Param("partnerId") Long partnerId);
}
