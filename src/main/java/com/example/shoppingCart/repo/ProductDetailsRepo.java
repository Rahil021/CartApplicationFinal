package com.example.shoppingCart.repo;

import com.example.shoppingCart.Models.Database.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailsRepo extends JpaRepository<ProductDetails, Integer> {

}
