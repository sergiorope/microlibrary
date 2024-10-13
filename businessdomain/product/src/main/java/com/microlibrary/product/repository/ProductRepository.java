/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.product.repository;

import com.microlibrary.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sergio
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
