package com.example.shoppingCart.Models.ResponseModels.Relationship;

import com.example.shoppingCart.Models.RequestModel.Customer;
import com.example.shoppingCart.Models.ResponseModels.BasketInfoResponse;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasketInfoResponseWithRelationship extends BasketInfoResponse {

    @JsonProperty("relationships")
    private CustomerShell customerShell;

}
