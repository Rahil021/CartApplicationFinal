package com.example.shoppingCart.Controller;

import com.example.shoppingCart.Common.ApiResponse;
import com.example.shoppingCart.newModels.BasketData;
import com.example.shoppingCart.newModels.nBasketInfo;
import com.example.shoppingCart.newModels.nProductDetails;
import com.example.shoppingCart.newModels.nProducts;
import com.example.shoppingCart.repo.BasketRepository;
import com.example.shoppingCart.repo.ProductRepository;
import com.example.shoppingCart.Models.Basket;
import com.example.shoppingCart.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shoppingCart.Service.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@RequestMapping("/basket")
@RestController
public class BasketController {

    @Autowired
    Shoppingcartservice shoppingcartservice;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<Object> getProducts(){

        List<Product> result = shoppingcartservice.showAllProducts();

       // ResponseEntity res =  new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been added",map), HttpStatus.CREATED);
        return ApiResponse.generateResponse("Successfully added data!", HttpStatus.OK, result);
    }

    @GetMapping("/basket")
    public ResponseEntity<Object> getCartItems() {
        List<Basket> result = shoppingcartservice.showBasket();

        return ApiResponse.generateResponse("success", HttpStatus.OK, result);
    }

    @GetMapping("/basket/v2")
    public List<BasketData> getBaskets2(){

        List<BasketData> list = new ArrayList<>();

        BasketData data = new BasketData();
        // data.setData(); //4

        nBasketInfo basketInfo = new nBasketInfo();
        basketInfo.setId(1);
        basketInfo.setType(nBasketInfo.TypeEnum.BASKET);
        //  basketInfo.setProducts();  //3

        nProducts products = new nProducts();
        //     products.setProducts(); //2

        List<nProductDetails> productDetails = new ArrayList<>();
        nProductDetails details = new nProductDetails();
        details.setProduct_id("11");
        details.setProduct_quantity(10);

        productDetails.add(details); //1

        details.setProduct_id("21");
        details.setProduct_quantity(10);

        productDetails.add(details);

        products.setProducts(productDetails); //2

        basketInfo.setProducts(products); //3

        data.setData(basketInfo); //1

        list.add(data);

        return list;
    }

}
