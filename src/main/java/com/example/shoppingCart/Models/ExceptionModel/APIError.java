package com.example.shoppingCart.Models.ExceptionModel;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.persistence.Embedded;

@Data
public class APIError {

    private HttpStatus status;
    private String code;
    private String title;
    @Embedded
    private ErrorSource source;
}