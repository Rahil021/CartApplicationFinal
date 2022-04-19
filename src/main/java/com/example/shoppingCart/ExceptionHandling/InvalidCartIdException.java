package com.example.shoppingCart.ExceptionHandling;

public class InvalidCartIdException extends Exception{

    private static final String DEFAULT_MESSAGE = "Basket Id not found";

    private static final String EXTENDED_MESSAGE = "";

    public InvalidCartIdException() {
        super(DEFAULT_MESSAGE);
    }

}
