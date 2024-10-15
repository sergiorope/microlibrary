/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.loanline.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
 * @author Sergio
 */
@Schema(name = "LoanlineResponse", description = "Model representing a loanline in the database")
@Data
public class LoanlineResponse {
    
    @Schema(name = "id", required = true, example = "1", defaultValue = "1", description = "Unique ID of the loanline")
    private long id;  
    
    @Schema(name = "product_Id", required = true, example = "2", defaultValue = "2", description = "ID of the product associated with the loanline")
    private long product_Id;

    @Schema(name = "loan_Id", required = true, example = "3", defaultValue = "3", description = "ID of the loan associated with the loanline")
    private long loan_Id;

}
