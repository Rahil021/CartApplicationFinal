package com.example.shoppingCart.StockModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockInfo {

    private String supplier;

    @NotNull(message = "Enter associated product Id")
    private String productId;

    @Min(0)
    private Long quantity;
}