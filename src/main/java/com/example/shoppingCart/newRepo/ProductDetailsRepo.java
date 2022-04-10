package com.example.shoppingCart.newRepo;

import com.example.shoppingCart.newModels.nProductDetails;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailsRepo extends JpaRepository<nProductDetails, Integer> {
}
