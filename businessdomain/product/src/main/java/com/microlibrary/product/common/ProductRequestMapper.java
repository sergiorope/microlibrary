package com.microlibrary.product.common;

import com.microlibrary.product.dto.ProductRequest;
import com.microlibrary.product.entities.Product;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

    @Mappings({
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "autor", target = "autor"),
        @Mapping(source = "image", target = "image")
    })
    Product ProductRequestToProduct(ProductRequest source);

    List<Product> ProductRequestListToProductList(List<ProductRequest> source);

    @InheritInverseConfiguration
    ProductRequest ProductToProductRequest(Product source);

    @InheritInverseConfiguration
    List<ProductRequest> ProductListToProductRequestList(List<Product> source);
}
