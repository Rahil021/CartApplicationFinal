package com.example.shoppingCart.repo;

import com.example.shoppingCart.Models.Database.BasketInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketInfoRepo extends JpaRepository<BasketInfo,Integer>{
}
