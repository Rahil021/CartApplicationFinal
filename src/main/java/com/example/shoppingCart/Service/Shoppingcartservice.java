package com.example.shoppingCart.Service;

import com.example.shoppingCart.ExceptionHandling.*;
import com.example.shoppingCart.Models.Database.BasketInfo;
import com.example.shoppingCart.Models.Database.ProductDetails;
import com.example.shoppingCart.Models.ExceptionModel.APIError;
import com.example.shoppingCart.Models.ExceptionModel.ErrorResponse;
import com.example.shoppingCart.Models.RequestModel.Customer;
import com.example.shoppingCart.Models.RequestModel.UpdateQuantity;
import com.example.shoppingCart.Models.ResponseModels.BasketInfoResponse;
import com.example.shoppingCart.Models.ResponseModels.Relationship.BasketInfoResponseWithRelationship;
import com.example.shoppingCart.Models.ResponseModels.EmptyBasket;
import com.example.shoppingCart.Models.ResponseModels.Relationship.CustomerShell;
import com.example.shoppingCart.Models.ResponseModels.nProducts;
import com.example.shoppingCart.StockModels.StockResponse;
import com.example.shoppingCart.repo.BasketInfoRepo;
import com.example.shoppingCart.repo.ProductDetailsRepo;
import com.fasterxml.jackson.core.JsonParser;
import lombok.Builder;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class Shoppingcartservice {

    @Autowired
    RestTemplate restTemplate;

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

    public List<BasketInfoResponse> getBasketItemsById(Long basketId) throws InvalidCartIdException {

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
            // invalid cart id exception
            throw new InvalidCartIdException();
        }else{
            return output;
        }
    }

    public void addProductToBasket(Long basketId, ProductDetails details) throws InvalidCartIdException, AlreadySubmittedException, InvalidProductIdException, NotEnoughProductsInStockException {
        StockResponse stockResponse;
        Boolean flag;

        try{
            stockResponse = restTemplate.getForObject("http://localhost:9090/stock/p/"+details.getProduct_id(), StockResponse.class);
        }catch (HttpClientErrorException e){
            throw new InvalidProductIdException();
        }

        Long quantity =  stockResponse.getData().get(0).getAttributes().getQuantity();

        List<BasketInfo> list = basketInfoRepo.findAll();
        flag = false;

        for (BasketInfo item : list) {

            if (item.getId().equals(basketId)) {

                flag = true;

                if (item.getStatus() == 1) {

                    if(details.getProduct_quantity() <= quantity){

                        ProductDetails productDetails = new ProductDetails();
                        productDetails.setProduct_id(details.getProduct_id());
                        productDetails.setProduct_quantity(details.getProduct_quantity());
                        productDetails.setCart_id(basketId);
                        productDetailsRepo.save(productDetails);
                        break;

                    }else {
                        throw new NotEnoughProductsInStockException();
                    }

                } else {
                    throw new AlreadySubmittedException();
                }
            }

        }

        if (!flag)
            throw new InvalidCartIdException();

    }

    public void updateQuantity(Long basketId, ProductDetails product) throws InvalidCartIdException, InvalidProductIdException, AlreadySubmittedException, NotEnoughProductsInStockException {

        List<BasketInfo> list = basketInfoRepo.findAll();
        Boolean flagBasket = false;
        Boolean flagProduct = false;
        StockResponse stockResponse;

        try{
            stockResponse = restTemplate.getForObject("http://localhost:9090/stock/p/"+product.getProduct_id(), StockResponse.class);
        }catch (HttpClientErrorException e){
            throw new InvalidProductIdException();
        }

        Long quantity =  stockResponse.getData().get(0).getAttributes().getQuantity();
        System.out.println(quantity);

        for (BasketInfo item : list){
            if(item.getId().equals(basketId)){

                flagBasket = true;

                if(item.getStatus() == 1){

                    if(product.getProduct_quantity() <= quantity){

                        List<ProductDetails> cartProductsAll = productDetailsRepo.findAll();
                        List<ProductDetails> sortByCartId = new ArrayList<>();

                        for(ProductDetails s: cartProductsAll){
                            if(s.getCart_id().equals(basketId)){
                                sortByCartId.add(s);
                            }
                        }

                        for(ProductDetails s: sortByCartId){
                            if(s.getProduct_id().equals(product.getProduct_id())){

                                ProductDetails updatedProduct = new ProductDetails();
                                updatedProduct.setProduct_id(s.getProduct_id());
                                updatedProduct.setProduct_quantity(product.getProduct_quantity());
                                updatedProduct.setCart_id(s.getCart_id());

                                productDetailsRepo.delete(s);
                                productDetailsRepo.save(updatedProduct);

                                flagProduct = true;

                            }
                        }

                    }else {
                        throw new NotEnoughProductsInStockException();
                    }

                }else{
                    // Basket already submitted exception
                    throw new AlreadySubmittedException();
                }
            }
        }

        if(!flagBasket)
            throw new InvalidCartIdException();
        else if(!flagProduct)
            throw new InvalidProductIdException();

    }

    public void associateBasketWithCustomer(Long basketId, Customer customer) throws InvalidCartIdException, CustomerAlreadyAssocException, AlreadySubmittedException {

        List<BasketInfo> list = basketInfoRepo.findAll();
        List<BasketInfoResponse> output = new ArrayList<>();
        Boolean flag = false;

        for(BasketInfo item : list){
            if(item.getId().equals(basketId)){

                if(item.getStatus() == 1){

                    if(item.getCustomerId() == null){

                        BasketInfo response = new BasketInfo();
                        response.setId(item.getId());
                        response.setType(item.getType());
                        response.setList(item.getList());
                        response.setCustomerId(customer.getCustomerId());

                        //basketInfoRepo.delete(item);
                        basketInfoRepo.save(response);

                        flag = true;
                        break;

                    }else{
                        //throw new Customer Already Associated Exception
                        throw new CustomerAlreadyAssocException();
                    }

                }else{
                    // Basket already submitted exception
                    throw new AlreadySubmittedException();
                }

            }
        }

        if(!flag){
            //throw cart id not found exception
            throw new InvalidCartIdException();
        }

    }

    public List<BasketInfoResponseWithRelationship> submitBasket(Long basketId) throws AlreadySubmittedException, InvalidCartIdException, CustomerNotAssocException, InvalidProductIdException, NotEnoughProductsInStockException {

       //calling Stock api to change stock of product
        List<BasketInfo> list = basketInfoRepo.findAll();

        List<BasketInfoResponseWithRelationship> output = new ArrayList<>();

        List<ProductDetails> productListForReducingQuantity  = new ArrayList<>();

        boolean flag = false;
        boolean customerAssociated = false;
        boolean AllCOnditionSatisfied = false;

        for (BasketInfo item : list){
            if(item.getId().equals(basketId)){

                flag=true;

                if(item.getCustomerId() != null){

                    customerAssociated = true;

                    if(item.getStatus() == 1){  //change to 1

                        AllCOnditionSatisfied = true;

                        BasketInfoResponseWithRelationship response = new BasketInfoResponseWithRelationship();
                        response.setId(item.getId());
                        response.setType(BasketInfoResponse.TypeEnum.BASKET);

                        nProducts products = new nProducts();
                        products.setProducts(item.getList());

                        productListForReducingQuantity = item.getList();

                        response.setProducts(products);

                        Customer customer = new Customer();
                        customer.setCustomerId(item.getCustomerId());

                        CustomerShell customerShell = new CustomerShell();
                        customerShell.setCustomer(customer);

                        response.setCustomerShell(customerShell);

                        item.setStatus(0); //1 - active, 0 - completed
                        basketInfoRepo.save(item);

                        output.add(response);
                        break;

                    }else{
                        // Basket already submitted exception
                        throw new AlreadySubmittedException();
                    }

                }

            }
        }

    if(!flag)
        throw new InvalidCartIdException();
    else if (!customerAssociated)
        throw new CustomerNotAssocException();

    if(AllCOnditionSatisfied){

        StockResponse stockResponse;

        for(ProductDetails qlist : productListForReducingQuantity){
            Long id = qlist.getProduct_id();
            Long quantity = qlist.getProduct_quantity();

            //add stock body and call restTemplate in try catch block

            try{
                stockResponse = restTemplate.getForObject("http://localhost:9090/stock/p/"+id, StockResponse.class);
            }catch (HttpClientErrorException e){
                throw new InvalidProductIdException();
            }

            Long dbQuantity = stockResponse.getData().get(0).getAttributes().getQuantity();

            if(dbQuantity >= quantity){

                UpdateQuantity updateQuantity = new UpdateQuantity();
                updateQuantity.setProductId(String.valueOf(id));
                updateQuantity.setQuantity(quantity);


                try{
                    restTemplate.put("http://localhost:9090/stock/removeStock/"+id,updateQuantity,StockResponse.class);
                }catch (HttpClientErrorException e){

                    throw new InvalidProductIdException();
                }

            }else {
                throw new NotEnoughProductsInStockException();
            }

        }

    }

        return output;

    }

}
