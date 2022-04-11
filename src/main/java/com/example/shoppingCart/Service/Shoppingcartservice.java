package com.example.shoppingCart.Service;

import com.example.shoppingCart.ExceptionHandling.InvalidCartIdException;
import com.example.shoppingCart.Models.Database.BasketInfo;
import com.example.shoppingCart.Models.Database.ProductDetails;
import com.example.shoppingCart.Models.ResponseModels.BasketInfoResponse;
import com.example.shoppingCart.Models.ResponseModels.EmptyBasket;
import com.example.shoppingCart.Models.ResponseModels.nProducts;
import com.example.shoppingCart.repo.BasketInfoRepo;
import com.example.shoppingCart.repo.ProductDetailsRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class Shoppingcartservice {

    @Autowired
    BasketInfoRepo basketInfoRepo;

    @Autowired
    ProductDetailsRepo productDetailsRepo;

    public EmptyBasket createBasket(){

        BasketInfo add = new BasketInfo();
        add.setType(BasketInfo.TypeEnum.BASKET);
        basketInfoRepo.save(add);

        EmptyBasket emptyBasket = new EmptyBasket();
        emptyBasket.setId(add.getId());
        emptyBasket.setType("basket");

        return emptyBasket;

    }

    @SneakyThrows
    public List<BasketInfoResponse> getBasketItemsById(Integer basketId){

        List<BasketInfo> list = basketInfoRepo.findAll();

        List<BasketInfoResponse> output = new ArrayList<>();

        boolean flag = false;

        for (BasketInfo item : list){
            if(item.getId().equals(basketId)){

                BasketInfoResponse response = new BasketInfoResponse();
                response.setId(item.getId());
                response.setType(BasketInfoResponse.TypeEnum.BASKET);

                nProducts products = new nProducts();
                products.setProducts(item.getList());

                response.setProducts(products);
                output.add(response);
                flag = true;
                break;
            }
        }

        if(!flag){
            throw new InvalidCartIdException();
        }else{
            return output;
        }
    }

    public void addProductToBasket(Integer basketId, ProductDetails details){

        ProductDetails productDetails = new ProductDetails();

        productDetails.setProduct_id(details.getProduct_id());
        productDetails.setProduct_quantity(details.getProduct_quantity());
        productDetails.setCart_id(basketId);

        productDetailsRepo.save(productDetails);

    }

    public void updateQuantity(Integer basketId, Integer productId, ProductDetails product){

    }

}

/*

 for (nBasketInfo item : list){
            if(item.getId().equals(basketId)){
                output.add(item);
                flag = true;
                break;
            }
        }

 */