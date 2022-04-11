package com.example.shoppingCart.ExceptionHandling;

public class InvalidCartIdException extends Exception{

    private static final String DEFAULT_MESSAGE = "Cart Id not found";

    public InvalidCartIdException() {
        super(DEFAULT_MESSAGE);
    }

}
