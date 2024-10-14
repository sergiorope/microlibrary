package com.microlibrary.customer.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author Sergio
 */
@Data
@Entity
@Schema(name = "Customer", description = "Model represent a customer on database")

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;

    @Schema(description = "The ID of the category or rank of the customer")
    private Long partnerId;

}
