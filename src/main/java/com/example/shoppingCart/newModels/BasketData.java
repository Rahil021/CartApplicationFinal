package com.example.shoppingCart.newModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketData {

    @JsonProperty("data")
    private nBasketInfo data = null;

}
