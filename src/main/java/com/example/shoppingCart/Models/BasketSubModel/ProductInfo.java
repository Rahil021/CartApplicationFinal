package com.example.shoppingCart.Models.BasketSubModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductInfo {

    @Column(name = "product_id")
    public Integer product_id;

    @Column(name = "quantity")
    public Integer quantity;


}
