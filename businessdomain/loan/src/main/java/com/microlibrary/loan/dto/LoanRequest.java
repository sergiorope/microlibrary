package com.microlibrary.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Sergio
 */
@Schema(name = "LoanRequest", description = "Model representing a loan in the database")
@Data
public class LoanRequest {


    @Schema(name = "start_date", required = true, example = "15-10-2024", defaultValue = "15-10-2024", description = "Start date of loan")
    private String start_date;

    @Schema(name = "end_date", required = true, example = "15-01-2025", defaultValue = "15-01-2025", description = "End date of loan")
    private String end_date;

    @Schema(name = "status", required = true, example = "En proceso", defaultValue = "En proceso", description = "Status of loan")
    private String status;

    @Schema(name = "customer_Id", required = true, example = "1", defaultValue = "1", description = "Customer associated with the loan")
    private Long customer_Id;
    
   
 
}
