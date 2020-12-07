package com.kincarta.ecommerce.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@Builder
@EqualsAndHashCode
public class Item {

    private String id;
    private String name;
    private Double price;
}
