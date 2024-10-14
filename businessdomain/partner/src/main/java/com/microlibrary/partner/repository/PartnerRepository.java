/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.partner.repository;


import com.microlibrary.partner.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sergio
 */
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    
}
