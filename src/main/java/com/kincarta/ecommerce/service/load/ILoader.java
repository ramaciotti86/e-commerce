package com.kincarta.ecommerce.service.load;

import com.kincarta.ecommerce.model.Item;
import com.kincarta.ecommerce.model.PromotionalRules;

import java.util.List;

public interface ILoader {
    List<Item> getAllItems();

    List<PromotionalRules> getDiscountItems();
}
