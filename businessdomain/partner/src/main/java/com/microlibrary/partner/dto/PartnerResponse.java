/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.partner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
 * @author Sergio
 */
@Schema(name = "PartnerResponse", description = "Model representing a product in the database")
@Data
public class PartnerResponse {
    
    @Schema(name = "id", required = true, example = "1", defaultValue = "1", description = "Unique ID of the partner")
    private long id;  
    
     @Schema(name = "name", required = true, example = "Regular", defaultValue = "Regular", description = "name of partner")
    private String name;

    @Schema(name = "description", required = true, example = "Regular shopping", defaultValue = "Regular shopping", description = "Description of partner")
    private String description;
}
