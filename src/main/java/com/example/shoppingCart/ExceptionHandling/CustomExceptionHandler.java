package com.example.shoppingCart.ExceptionHandling;


import com.example.shoppingCart.Models.ExceptionModel.APIError;
import com.example.shoppingCart.Models.ExceptionModel.ErrorSource;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidCartIdException.class})
    ResponseEntity<?> InvalidCartIdException(Exception e, ServletWebRequest request){
        APIError apiError = new APIError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setCode("404");
        apiError.setTitle(e.getMessage());
        ErrorSource es = new ErrorSource();
        es.setParameter("basketId");
        es.setPointer("N/A");
        apiError.setSource(es);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({InvalidProductIdException.class})
    ResponseEntity<?> InvalidProductIdException(Exception e, ServletWebRequest request){
        APIError apiError = new APIError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setCode("404");
        apiError.setTitle(e.getMessage());
        ErrorSource es = new ErrorSource();
        es.setParameter("productId");
        es.setPointer("N/A");
        apiError.setSource(es);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({NotEnoughProductsInStockException.class})
    ResponseEntity<?> NotEnoughProductsInStockException(Exception e, ServletWebRequest request){

        APIError apiError = new APIError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setCode("400");
        apiError.setTitle(e.getMessage());
        ErrorSource es = new ErrorSource();
        es.setParameter("quantity");
        es.setPointer("N/A");
        apiError.setSource(es);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({CustomerAlreadyAssocException.class})
    ResponseEntity<?> CustomerAlreadyAssocException(Exception e, ServletWebRequest request){
        APIError apiError = new APIError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setCode("400");
        apiError.setTitle(e.getMessage());
        ErrorSource es = new ErrorSource();
        es.setParameter("Customer");
        es.setPointer("N/A");
        apiError.setSource(es);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }
    @ExceptionHandler({AlreadySubmittedException.class})
    ResponseEntity<?> AlreadySubmittedException(Exception e, ServletWebRequest request){
        APIError apiError = new APIError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setCode("400");
        apiError.setTitle(e.getMessage());
        ErrorSource es = new ErrorSource();
        es.setParameter("Basket");
        es.setPointer("N/A");
        apiError.setSource(es);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({CustomerNotAssocException.class})
    ResponseEntity<?> CustomerNotAssocException(Exception e, ServletWebRequest request){
        APIError apiError = new APIError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setCode("400");
        apiError.setTitle(e.getMessage());
        ErrorSource es = new ErrorSource();
        es.setParameter("Customer");
        es.setPointer("N/A");
        apiError.setSource(es);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }


}