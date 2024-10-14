/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.customer.common;


import com.microlibrary.customer.dto.CustomerResponse;
import com.microlibrary.customer.entities.Customer;
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
public interface CustomerResponseMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "surname", target = "surname"),
        @Mapping(source = "partnerId", target = "partnerId")
       
    })
    CustomerResponse CustomerToCustomerResponse(Customer source);

    List<CustomerResponse> CustomerListToCustomerResponseList(List<Customer> source);

    @InheritInverseConfiguration
    Customer CustomerResponseToCustomer(CustomerResponse srr);

    @InheritInverseConfiguration
    List<Customer> CustomerResponseToCustomerList(List<CustomerResponse> source);
}
