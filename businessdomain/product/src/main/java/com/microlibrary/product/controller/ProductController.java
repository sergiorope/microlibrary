/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.product.controller;

import com.microlibrary.product.dto.ProductResponse;
import com.microlibrary.product.entities.Product;
import com.microlibrary.product.service.ProductService;
import com.microservice.product.exception.BussinesRuleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sergio
 */
@Tag(name = "Product API", description = "This APi serve all functionality for management Products")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService ps;

    @Operation(description = "FindAll Products", summary = "Return 400 if the bussines validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/list")
    public ResponseEntity<?> getAll() throws BussinesRuleException {

        List<ProductResponse> findAll = ps.getAll();

        return ResponseEntity.ok(findAll);

    }

}
