package com.microlibrary.product.service;

import com.microlibrary.product.dto.ProductRequest;
import com.microlibrary.product.dto.ProductResponse;
import com.microlibrary.product.exception.BussinesRuleException;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAll() throws BussinesRuleException;

    ProductResponse getById(long id) throws BussinesRuleException;

    ProductResponse post(ProductRequest input) throws BussinesRuleException;

    ProductResponse put(long id, ProductRequest input) throws BussinesRuleException;

    ProductResponse delete(long id) throws BussinesRuleException;
}
