package com.example.shoppingCart.StockModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    @Embedded
    private List<Stock> data;
}

