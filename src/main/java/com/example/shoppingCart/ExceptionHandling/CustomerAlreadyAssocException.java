package com.example.shoppingCart.ExceptionHandling;

public class CustomerAlreadyAssocException extends Exception{

    private static final String DEFAULT_MESSAGE = "Customer is Already Associated";

    public CustomerAlreadyAssocException() {
        super(DEFAULT_MESSAGE);
    }

}
