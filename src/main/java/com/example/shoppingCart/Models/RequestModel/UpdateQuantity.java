package com.example.shoppingCart.Models.RequestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuantity {

    private String productId;
    private Long quantity;
    private String supplier;

}
