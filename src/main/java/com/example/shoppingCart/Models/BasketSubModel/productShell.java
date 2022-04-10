package com.example.shoppingCart.Models.BasketSubModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor
public class productShell {

    @Embedded
    private ProductInfo products;

}
