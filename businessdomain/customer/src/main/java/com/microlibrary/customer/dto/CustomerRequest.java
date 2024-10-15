/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.customer.dto;

import com.microlibrary.customer.entities.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
 * @author Sergio
 */
@Schema(name = "CustomerRequest", description = "Model represent a Customer on database")
@Data
public class CustomerRequest {

    @Schema(name = "name", required = true, example = "Diego", defaultValue = "Diego", description = "Name of customer")
    private String name;

    @Schema(name = "surname", required = true, example = "Sánchez", defaultValue = "Sánchez", description = "Surname of customer")
    private String surname;

    @Schema(name = "partner_Id", required = true, example = "1", defaultValue = "1", description = "Id represent the relation whit partner")

    private long partner_Id;

}
