package com.example.shoppingCart.Service;

import com.example.shoppingCart.ExceptionHandling.AlreadySubmittedException;
import com.example.shoppingCart.ExceptionHandling.CustomerAlreadyAssocException;
import com.example.shoppingCart.ExceptionHandling.InvalidCartIdException;
import com.example.shoppingCart.ExceptionHandling.InvalidProductIdException;
import com.example.shoppingCart.Models.Database.BasketInfo;
import com.example.shoppingCart.Models.Database.ProductDetails;
import com.example.shoppingCart.Models.RequestModel.Customer;
import com.example.shoppingCart.Models.ResponseModels.BasketInfoResponse;
import com.example.shoppingCart.Models.ResponseModels.Relationship.BasketInfoResponseWithRelationship;
import com.example.shoppingCart.Models.ResponseModels.EmptyBasket;
import com.example.shoppingCart.Models.ResponseModels.Relationship.CustomerShell;
import com.example.shoppingCart.Models.ResponseModels.nProducts;
import com.example.shoppingCart.repo.BasketInfoRepo;
import com.example.shoppingCart.repo.ProductDetailsRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BasketInfoResponse> getBasketItemsById(Integer basketId) throws InvalidCartIdException {

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

    public void addProductToBasket(Integer basketId, ProductDetails details) throws InvalidCartIdException, AlreadySubmittedException {

        List<BasketInfo> list = basketInfoRepo.findAll();

        for (BasketInfo item : list){
            if(item.getId().equals(basketId)){
                if(item.getStatus() == 1){
                    ProductDetails productDetails = new ProductDetails();

                    productDetails.setProduct_id(details.getProduct_id());
                    productDetails.setProduct_quantity(details.getProduct_quantity());
                    productDetails.setCart_id(basketId);

                    productDetailsRepo.save(productDetails);
                    break;
                }else{
                    // Basket already submitted exception
                    throw new AlreadySubmittedException();
                }
            }else {
                // basket id not found exception
                throw new InvalidCartIdException();
            }
        }
    }

    public void updateQuantity(Integer basketId, ProductDetails product) throws InvalidCartIdException, InvalidProductIdException, AlreadySubmittedException {

        List<BasketInfo> list = basketInfoRepo.findAll();

        for (BasketInfo item : list){
            if(item.getId().equals(basketId)){
                if(item.getStatus() == 1){

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

                        }else{
                            //product not found in cart exception
                            throw new InvalidProductIdException();
                        }
                    }

                }else{
                    // Basket already submitted exception
                    throw new AlreadySubmittedException();
                }
            }else{
                // basket id not found exception
                throw new InvalidCartIdException();
            }
        }

    }

    public void associateBasketWithCustomer(Integer basketId, Customer customer) throws InvalidCartIdException, CustomerAlreadyAssocException, AlreadySubmittedException {

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

    public List<BasketInfoResponseWithRelationship> submitBasket(Integer basketId) throws AlreadySubmittedException, InvalidCartIdException {

        List<BasketInfo> list = basketInfoRepo.findAll();

        List<BasketInfoResponseWithRelationship> output = new ArrayList<>();

        boolean flag = false;

        for (BasketInfo item : list){
            if(item.getId().equals(basketId)){

                if(item.getStatus() == 1){

                    BasketInfoResponseWithRelationship response = new BasketInfoResponseWithRelationship();
                    response.setId(item.getId());
                    response.setType(BasketInfoResponse.TypeEnum.BASKET);

                    nProducts products = new nProducts();
                    products.setProducts(item.getList());

                    response.setProducts(products);

                    Customer customer = new Customer();
                    customer.setCustomerId(item.getCustomerId());

                    CustomerShell customerShell = new CustomerShell();
                    customerShell.setCustomer(customer);

                    response.setCustomerShell(customerShell);

                    item.setStatus(0); //1 - active, 0 - completed
                    basketInfoRepo.save(item);

                    output.add(response);
                    flag = true;
                    break;

                }else{
                    // Basket already submitted exception
                    throw new AlreadySubmittedException();
                }

            }
        }

        if(!flag){
            throw new InvalidCartIdException();
        }else{
            return output;
        }

    }

}
