package com.example.shoppingCart.Controller;

import com.example.shoppingCart.ExceptionHandling.*;
import com.example.shoppingCart.Models.Database.ProductDetails;
import com.example.shoppingCart.Models.RequestModel.Customer;
import com.example.shoppingCart.Models.ResponseModels.*;
import com.example.shoppingCart.Models.ResponseModels.Relationship.BasketDataForRelationShip;
import com.example.shoppingCart.Models.ResponseModels.Relationship.BasketInfoResponseWithRelationship;
import com.example.shoppingCart.StockModels.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.shoppingCart.Service.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BasketController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Shoppingcartservice shoppingcartservice;

    // creates a new empty basket
    @PostMapping("/basket")
    public EmptyBasket createBasket(){
        return shoppingcartservice.createBasket();
    }

    // Get items from basket by id
    @GetMapping("/basket/{basketId}")
    public BasketData getBasketItemsById(@PathVariable Long basketId) throws InvalidCartIdException {

        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }


    //add product to basket
    @PostMapping("/basket/{basketId}/product")
    public BasketData addProductToBasket(@PathVariable Long basketId, @RequestBody ProductDetails productDetails) throws InvalidCartIdException, AlreadySubmittedException, InvalidProductIdException {


        shoppingcartservice.addProductToBasket(basketId,productDetails);
        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }

    //update product quantity in basket
    @PutMapping("/basket/{basketId}/product")
    public BasketData updateQuantity(@PathVariable Long basketId, @RequestBody ProductDetails product) throws InvalidCartIdException, InvalidProductIdException, AlreadySubmittedException {

        shoppingcartservice.updateQuantity(basketId,product);

        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }

    //submit basket
    @PostMapping("basket/submitBasket/{basketId}") //change to post
    public BasketDataForRelationShip submitBasket(@PathVariable Long basketId) throws AlreadySubmittedException, InvalidCartIdException, CustomerNotAssocException {

        //call stock update api and update stock_quantity

        List<BasketInfoResponseWithRelationship> list = new ArrayList<>();
        list = shoppingcartservice.submitBasket(basketId);

        BasketDataForRelationShip data = new BasketDataForRelationShip();
        data.setData(list);

        return data;
    }

    //Associate basket with customer
    @PutMapping("basket/{basketId}/customer")
    public BasketData associateBasketWithCustomer(@PathVariable Long basketId, @RequestBody Customer customer) throws InvalidCartIdException, CustomerAlreadyAssocException, AlreadySubmittedException {

        shoppingcartservice.associateBasketWithCustomer(basketId,customer);

        List<BasketInfoResponse> list;
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }
//    @GetMapping("/quantity/{productid}")
//    public StockResponse quantity(@PathVariable Long productid){
//
//        StockResponse stockResponse=restTemplate.getForObject("http://localhost:9090/stock/"+productid, StockResponse.class);
//
//        long quantity =  stockResponse.getData().get(0).getAttributes().getQuantity();
//        return stockResponse;
//    }
}

