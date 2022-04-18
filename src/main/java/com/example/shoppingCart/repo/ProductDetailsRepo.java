package com.example.shoppingCart.repo;

import com.example.shoppingCart.Models.Database.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepo extends JpaRepository<ProductDetails, Integer> {

}
