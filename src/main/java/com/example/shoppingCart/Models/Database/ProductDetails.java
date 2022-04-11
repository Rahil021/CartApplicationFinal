package com.example.shoppingCart.Models.Database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "CartProducts")
public class ProductDetails {

    @JsonIgnore
    public Integer cart_id;

    @Id
    @Column(name = "product_id")
    public Integer product_id;

    @Column(name = "quantity")
    public Integer product_quantity;

}
