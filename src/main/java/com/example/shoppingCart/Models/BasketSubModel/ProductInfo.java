package com.example.shoppingCart.Models.BasketSubModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductInfo {

    public Integer product_id;

    public Integer quantity;


}
