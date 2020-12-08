package com.kincarta.ecommerce.service;

import com.kincarta.ecommerce.model.Item;
import com.kincarta.ecommerce.model.PromotionalRules;
import com.kincarta.ecommerce.service.load.ILoader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class DiscountService {

    private ILoader loader;

    public Double getTotalPrice(String[] customerProductList) {
        return this.scan(customerProductList);
    }

    //Posso mudar esse metodo para uma classe especifica que toma conta desse "match" entre produtos e descontos
    public Double scan(String[] items){
        List<Item> allItems = new ArrayList(loader.getAllItems());
        List<PromotionalRules> discountItems = new ArrayList(loader.getDiscountItems());
        Double totalPrice = 0.0;

        ArrayList<String> duplications = getDuplications(items);

        for (String notRepeatedProduct : duplications) {
            String[] element = notRepeatedProduct.split(":");
            //element[0] -> product number
            //element[1] -> quantity
            Optional<PromotionalRules> productPromotionalRuleItem = discountItems.stream().filter(p -> element[0].equals(p.getProductId())).findFirst();
            if(productPromotionalRuleItem.isPresent()){
                //Posso melhorar pra pegar a quantidate (no caso 2) depois que achar ele na lista de promotionalRules
                if (Integer.valueOf(element[1]) >= productPromotionalRuleItem.get().getQty()){
                    productPromotionalRuleItem = discountItems.stream().filter(p -> element[0].equals(p.getProductId())).findFirst();
                    if (productPromotionalRuleItem.isPresent()){
                        totalPrice += Integer.valueOf(element[1]) * productPromotionalRuleItem.get().getPrice();
                    }else{
                        Optional<Item> productItem = allItems.stream().filter(p -> element[0].equals(p.getId())).findFirst();
                        totalPrice += Integer.valueOf(element[1]) * productItem.get().getPrice();
                    }
                }else{
                    Optional<Item> productItem = allItems.stream().filter(p -> element[0].equals(p.getId())).findFirst();
                    totalPrice += Integer.valueOf(element[1]) * productItem.get().getPrice();
                }
            }
        }

        //Posso melhorar para pegar esse valor total de algum outro lugar dinamico e nao colocar no codigo hardcoded
        if (totalPrice > 75){
            totalPrice = totalPrice - (totalPrice/10);
        }

        return Math.floor(totalPrice * 100) / 100;
    }

    private ArrayList<String> getDuplications(String[] items) {
        ArrayList<String> duplications = new ArrayList();
        List<String> list = Arrays.asList(items);
        ArrayList<String> listTwo = new ArrayList(list);

        HashSet hs = new HashSet<>();
        hs.addAll(listTwo);
        listTwo.clear();
        listTwo.addAll(hs);

        for(String word : listTwo){
            int count = Collections.frequency(list, word);
            String result = word + ":" + count;
            duplications.add(result);
        }
        return duplications;
    }
}
