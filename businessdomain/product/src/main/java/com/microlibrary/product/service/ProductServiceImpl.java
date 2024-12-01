/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.product.service;

import com.microlibrary.product.common.ProductRequestMapper;
import com.microlibrary.product.common.ProductResponseMapper;
import com.microlibrary.product.dto.ProductRequest;
import com.microlibrary.product.dto.ProductResponse;
import com.microlibrary.product.entities.Product;
import com.microlibrary.product.repository.ProductRepository;
import com.microlibrary.product.exception.BussinesRuleException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sergio
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository pr;

    @Autowired
    ProductRequestMapper prq;

    @Autowired
    ProductResponseMapper prp;

    public List<ProductResponse> getAll() throws BussinesRuleException {

        List<Product> findAll = pr.findAll();

        if (findAll.isEmpty()) {

            throw new BussinesRuleException("404", "Products not found", HttpStatus.NOT_FOUND);
        }

        List<ProductResponse> findAllResponse = prp.ProductListToProductResponseList(findAll);

        return findAllResponse;

    }

    public ProductResponse getById(long id) throws BussinesRuleException {

        Optional<Product> findById = pr.findById(id);

        if (!findById.isPresent()) {
            throw new BussinesRuleException("404", "Product not found", HttpStatus.NOT_FOUND);
        }

        Product product = findById.get();

        ProductResponse findIdResponse = prp.ProductToProductResponse(product);

        return findIdResponse;
    }

    public ProductResponse post(ProductRequest input) throws BussinesRuleException {

        if (input == null || input.getName() == null || input.getImage() == null
                || input.getName().isEmpty() || input.getImage().isEmpty()) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST);
        }

        Product post = prq.ProductRequestToProduct(input);

        pr.save(post);

        ProductResponse productResponse = prp.ProductToProductResponse(post);

        return productResponse;
    }

    public ProductResponse put(long id, ProductRequest input) throws BussinesRuleException {

        ProductResponse put = getById(id);

        if (put == null) {

            throw new BussinesRuleException("404", "Product not found", HttpStatus.NOT_FOUND);
        }

        put.setName(input.getName());
        put.setDescription(input.getDescription());
        put.setAutor(input.getAutor());
        put.setImage(input.getImage());

        if (put.getName().isEmpty() || put.getImage().isEmpty()) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.NOT_FOUND);
        }

        Product product = prp.ProductResponseToProduct(put);

        pr.save(product);

        ProductResponse productResponse = prp.ProductToProductResponse(product);

        return productResponse;
    }

    public ProductResponse delete(long id) throws BussinesRuleException {

        ProductResponse delete = getById(id);

        if (delete == null) {

            throw new BussinesRuleException("404", "Product not found", HttpStatus.NOT_FOUND);
        }

        Product product = prp.ProductResponseToProduct(delete);

        pr.delete(product);

        ProductResponse productResponse = prp.ProductToProductResponse(product);

        return productResponse;

    }

}
