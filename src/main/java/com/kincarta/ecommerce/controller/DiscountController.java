package com.kincarta.ecommerce.controller;

import com.kincarta.ecommerce.service.DiscountService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;import java.util.List;

@RestController
public class DiscountController {

    public final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/totalPrice", name = "getTotalPrice", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Double getTotalPrice(@RequestParam(value = "productList", required = true) String[] customerProductList){
        return discountService.getTotalPrice(customerProductList);
    }

}
