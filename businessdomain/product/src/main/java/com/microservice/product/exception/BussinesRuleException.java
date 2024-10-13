package com.microservice.product.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Sergio
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BussinesRuleException extends Exception{
    
    private long id;
    private String code;   
    private HttpStatus httpStatus;
    
     public BussinesRuleException(long id, String code, String message,HttpStatus httpStatus) {
        super(message);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BussinesRuleException(String code, String message,HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
    
    public BussinesRuleException(String message, Throwable cause) {
        super(message, cause);
    }   

}
