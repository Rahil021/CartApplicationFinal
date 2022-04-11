package com.example.shoppingCart.Controller;

import com.example.shoppingCart.Models.Database.ProductDetails;
import com.example.shoppingCart.Models.ResponseModels.BasketData;
import com.example.shoppingCart.Models.ResponseModels.BasketInfoResponse;
import com.example.shoppingCart.Models.ResponseModels.EmptyBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.shoppingCart.Service.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BasketController {

    @Autowired
    Shoppingcartservice shoppingcartservice;

    // creates an empty basket
    @PostMapping("/basket")
    public EmptyBasket createBasket(){
        return shoppingcartservice.createBasket();
    }

    // Get items from basket
    @GetMapping("/basket/{basketId}")
    public BasketData getBasketItemsById(@PathVariable Integer basketId){

        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }

    //add product to basket
    @PostMapping("/basket/{basketId}/product")
    public BasketData addProductToBasket(@PathVariable Integer basketId, @RequestBody ProductDetails productDetails){

        shoppingcartservice.addProductToBasket(basketId,productDetails);
        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }

    //update product quantity in basket
    @PutMapping("/basket/{basketId}/product/{productId}")
    public BasketData updateQuantity(@PathVariable Integer basketId,@PathVariable Integer productId, @RequestBody ProductDetails product){

        shoppingcartservice.updateQuantity(basketId,productId,product);

        List<BasketInfoResponse> list = new ArrayList<>();
        list = shoppingcartservice.getBasketItemsById(basketId);

        BasketData data = new BasketData();
        data.setData(list);

        return data;
    }

}

