package com.example.shoppingCart.newModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class nProductDetails {

    @Id
    private String product_id;

    private Integer product_quantity;

}
