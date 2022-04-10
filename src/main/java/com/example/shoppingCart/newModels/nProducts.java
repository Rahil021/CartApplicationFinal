package com.example.shoppingCart.newModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class nProducts {

    @JsonProperty("products")
    private List<nProductDetails> products;

}
