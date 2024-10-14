package com.microlibrary.customer.common;

import com.microlibrary.customer.dto.CustomerRequest;
import com.microlibrary.customer.entities.Customer;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "surname", target = "surname"),
        @Mapping(source = "partnerId", target = "partnerId")
    })
    Customer CustomerRequestToCustomer(CustomerRequest source);

    List<Customer> CustomerRequestListToCustomerList(List<CustomerRequest> source);

    @InheritInverseConfiguration
    CustomerRequest CustomerToCustomerRequest(Customer source);

    @InheritInverseConfiguration
    List<CustomerRequest> CustomerListToCustomerRequestList(List<Customer> source);
}
