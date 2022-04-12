package com.example.shoppingCart.Models.ResponseModels.Relationship;

import com.example.shoppingCart.Models.RequestModel.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerShell {

    @JsonProperty("customer")
    private Customer customer;

}
