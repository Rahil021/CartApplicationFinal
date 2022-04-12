package com.example.shoppingCart.ExceptionHandling;

public class CustomerNotAssocException extends Exception{

    private static final String DEFAULT_MESSAGE = "Customer is Not Associated";

    public CustomerNotAssocException() {
        super(DEFAULT_MESSAGE);
    }

}
