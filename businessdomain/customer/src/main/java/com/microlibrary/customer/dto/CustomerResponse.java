/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
 * @author Sergio
 */
@Schema(name = "CustomerResponse", description = "Model representing a product in the database")
@Data
public class CustomerResponse {
    
    @Schema(name = "id", required = true, example = "1", defaultValue = "1", description = "Unique ID of the customer")
    private long id;  
    
   @Schema(name = "name", required = true, example = "Diego", defaultValue = "Diego", description = "Name of customer")
    private String name;

    @Schema(name = "surname", required = true, example = "Sánchez", defaultValue = "Sánchez", description = "Surname of customer")
    private String surname;

    @Schema(name = "partnerId", required = true, example = "1", defaultValue = "1", description = "Id represent the relation whit partner")

    private String partnerId;
}
