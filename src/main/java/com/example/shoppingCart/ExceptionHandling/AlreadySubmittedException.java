package com.example.shoppingCart.ExceptionHandling;

public class AlreadySubmittedException extends Exception{

    private static final String DEFAULT_MESSAGE = "Basket already submitted";

    public AlreadySubmittedException() {
        super(DEFAULT_MESSAGE);
    }

}
