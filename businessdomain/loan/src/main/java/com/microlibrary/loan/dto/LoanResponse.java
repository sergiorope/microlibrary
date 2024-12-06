package com.microlibrary.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Sergio
 */
@Schema(name = "LoanResponse", description = "Model representing a loan in the database")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {
    
    @Schema(name = "id", required = true, example = "1", defaultValue = "1", description = "Unique ID of the loan")
    private long id;  
    
    @Schema(name = "start_date", required = true, example = "15-10-2024", defaultValue = "15-10-2024", description = "Start date of loan")
    private String start_date;

    @Schema(name = "end_date", required = true, example = "15-01-2025", defaultValue = "15-01-2025", description = "End date of loan")
    private String end_date;

    @Schema(name = "status", required = true, example = "En proceso", defaultValue = "En proceso", description = "Status of loan")
    private String status;

    @Schema(name = "customer_Id", required = true, example = "1", defaultValue = "1", description = "Customer associated with the loan")
    private Long customer_Id;
}
