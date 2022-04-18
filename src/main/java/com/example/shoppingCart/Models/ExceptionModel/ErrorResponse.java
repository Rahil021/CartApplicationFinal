package com.example.shoppingCart.Models.ExceptionModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embedded;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    @Embedded
    private APIError data;
}
