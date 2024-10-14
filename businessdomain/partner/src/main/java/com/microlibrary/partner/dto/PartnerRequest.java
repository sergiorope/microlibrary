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
@Schema(name = "PartnerRequest", description = "Model represent a partner on database")
@Data
public class PartnerRequest {

    @Schema(name = "name", required = true, example = "Regular", defaultValue = "Regular", description = "name of partner")
    private String name;

    @Schema(name = "description", required = true, example = "Regular shopping", defaultValue = "Regular shopping", description = "Description of partner")
    private String description;


}
