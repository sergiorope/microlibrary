/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Sergio
 */
@Schema(name = "ProductRequest", description = "Model represent a product on database")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @Schema(name = "name", required = true, example = "La isla de la mujer dormida", defaultValue = "La isla de la mujer dormida", description = "Name of product")
    private String name;

    @Schema(name = "description", required = true, example = "Una novela de mar, amor y aventuras en el Egeo durante los años de la Guerra Civil española", defaultValue = "Una novela de mar, amor y aventuras en el Egeo durante los años de la Guerra Civil española", description = "Description of product")
    private String description;

    @Schema(name = "autor", required = true, example = "Arturo Pérez Reverte", defaultValue = "Arturo Pérez Reverte", description = "Autor of product")

    private String autor;

    @Schema(name = "image", required = true, example = "https://imagessl4.casadellibro.com/a/l/s7/34/9788410299634.webp", defaultValue = "https://imagessl4.casadellibro.com/a/l/s7/34/9788410299634.webp", description = "URL from image of product")

    private String image;

}
