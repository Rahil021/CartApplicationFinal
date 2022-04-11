package com.example.shoppingCart.repo;

import com.example.shoppingCart.Models.Database.BasketInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketInfoRepo extends JpaRepository<BasketInfo,Integer>{
}
