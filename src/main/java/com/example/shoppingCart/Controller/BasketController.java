package com.example.shoppingCart.Controller;

import com.example.shoppingCart.ExceptionHandling.AlreadySubmittedException;
import com.example.shoppingCart.ExceptionHandling.CustomerAlreadyAssocException;
import com.example.shoppingCart.ExceptionHandling.InvalidCartIdException;
import com.example.shoppingCart.ExceptionHandling.InvalidProductIdException;
import com.example.shoppingCart.Models.Database.ProductDetails;
import com.example.shoppingCart.Models.RequestModel.Customer;
import com.example.shoppingCart.Models.ResponseModels.*;
import com.example.shoppingCart.Models.ResponseModels.Relationship.BasketDataForRelationShip;
import com.example.shoppingCart.Models.ResponseModels.Relationship.BasketInfoResponseWithRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.shoppingCart.Service.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BasketController {

    @Autowired
    Shoppingcartservice shoppingcartservice;

    // creates a new empty basket
    @PostMapping("/basket")
    public EmptyBasket createBasket(){
        return shoppingcartservice.createBasket();
    }

    // Get items from basket by id
    @GetMapping("/basket/{basketId}")
    public BasketData getBasketItemsById(@PathVariable Integer basketId) throws InvalidCartIdException {

        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }

    //add product to basket
    @PostMapping("/basket/{basketId}/product")
    public BasketData addProductToBasket(@PathVariable Integer basketId, @RequestBody ProductDetails productDetails) throws InvalidCartIdException, AlreadySubmittedException {

        shoppingcartservice.addProductToBasket(basketId,productDetails);
        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }

    //update product quantity in basket
    @PutMapping("/basket/{basketId}/product")
    public BasketData updateQuantity(@PathVariable Integer basketId, @RequestBody ProductDetails product) throws InvalidCartIdException, InvalidProductIdException, AlreadySubmittedException {

        shoppingcartservice.updateQuantity(basketId,product);

        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }

    //submit basket
    @PostMapping("basket/submitBasket/{basketId}") //change to post
    public BasketDataForRelationShip submitBasket(@PathVariable Integer basketId) throws AlreadySubmittedException, InvalidCartIdException {

        //call stock update api and update stock_quantity

        List<BasketInfoResponseWithRelationship> list = new ArrayList<>();
        list = shoppingcartservice.submitBasket(basketId);

        BasketDataForRelationShip data = new BasketDataForRelationShip();
        data.setData(list);

        return data;
    }

    //Associate basket with customer
    @PutMapping("basket/{basketId}/customer")
    public BasketData associateBasketWithCustomer(@PathVariable Integer basketId, @RequestBody Customer customer) throws InvalidCartIdException, CustomerAlreadyAssocException, AlreadySubmittedException {

        shoppingcartservice.associateBasketWithCustomer(basketId,customer);

        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }
}

