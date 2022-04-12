package com.example.shoppingCart.ExceptionHandling;

public class InvalidProductIdException extends Exception{

    private static final String DEFAULT_MESSAGE = "Product Id not found";

    public InvalidProductIdException() {
        super(DEFAULT_MESSAGE);
    }

}
