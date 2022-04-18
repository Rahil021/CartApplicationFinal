package com.example.shoppingCart.Models.ResponseModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmptyBasket {

    private Long id;
    private String type;
}
