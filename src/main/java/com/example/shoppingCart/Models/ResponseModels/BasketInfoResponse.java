package com.example.shoppingCart.Models.ResponseModels;

import com.example.shoppingCart.Models.RequestModel.Customer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketInfoResponse {

    @JsonProperty("id")
    private Integer id = null;

    public enum TypeEnum {
        BASKET("basket");

        private String value;

        TypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static BasketInfoResponse.TypeEnum fromValue(String text) {
            for (BasketInfoResponse.TypeEnum b : BasketInfoResponse.TypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("type")
    private BasketInfoResponse.TypeEnum type = null;

    @JsonProperty("attributes")
    private nProducts products;

}
