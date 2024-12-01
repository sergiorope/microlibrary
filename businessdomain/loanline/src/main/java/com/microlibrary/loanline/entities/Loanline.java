/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.loanline.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 *
 * @author Sergio
 */
@Data
@AllArgsConstructor
@Entity
@Schema(name = "Loanline", description = "Model represent a loanline on database")

public class Loanline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long product_Id;
    private long loan_Id;
    

}
