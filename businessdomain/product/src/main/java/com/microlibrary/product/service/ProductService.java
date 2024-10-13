/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.product.service;

import com.microlibrary.product.common.ProductRequestMapper;
import com.microlibrary.product.common.ProductResponseMapper;
import com.microlibrary.product.dto.ProductResponse;
import com.microlibrary.product.entities.Product;
import com.microlibrary.product.repository.ProductRepository;
import com.microservice.product.exception.BussinesRuleException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sergio
 */
@Service
public class ProductService {

    @Autowired
    ProductRepository pr;

    @Autowired
    ProductRequestMapper prq;

    @Autowired
    ProductResponseMapper prp;

    public List<ProductResponse> getAll() throws BussinesRuleException {

        List<Product> findAll = pr.findAll();

        List<ProductResponse> findAllResponse = prp.ProductListToProductResponseList(findAll);

        if (findAllResponse.isEmpty()) {

            throw new BussinesRuleException("404", "No se encontró ningún producto", HttpStatus.NOT_FOUND);
        }

        return findAllResponse;

    }

}
