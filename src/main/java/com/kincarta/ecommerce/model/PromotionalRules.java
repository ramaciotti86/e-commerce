package com.kincarta.ecommerce.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@Builder
@EqualsAndHashCode
public class PromotionalRules {

    private String productId;
    private Integer qty;
    private Double price;

}
