/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.product.common;

import com.microlibrary.product.dto.ProductResponse;
import com.microlibrary.product.entities.Product;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author sergio
 */
@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "autor", target = "autor"),
        @Mapping(source = "image", target = "image")
    })
    ProductResponse ProductToProductResponse(Product source);

    List<ProductResponse> ProductListToProductResponseList(List<Product> source);

    @InheritInverseConfiguration
    Product ProductResponseToProduct(ProductResponse srr);

    @InheritInverseConfiguration
    List<Product> ProductResponseToProductList(List<ProductResponse> source);
}
