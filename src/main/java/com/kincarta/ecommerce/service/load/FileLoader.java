package com.kincarta.ecommerce.service.load;

import com.kincarta.ecommerce.model.Item;
import com.kincarta.ecommerce.model.PromotionalRules;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileLoader implements ILoader{

    public List<Item> allItems = new ArrayList<>();
    public List<PromotionalRules> discountItems = new ArrayList<>();

    @PostConstruct
    private void init_price() {
        List<Item> allItems = new ArrayList<>();
        ClassPathResource cp = new ClassPathResource("data_item_price.csv");
        try {
            FileReader fileReader = new FileReader(cp.getFile());
            CSVReader csvReader = new CSVReaderBuilder(fileReader).build();
            String[] record;

            while((record = csvReader.readNext()) != null && !record[0].isEmpty()){
                Item item = Item.builder()
                        .id(record[0])
                        .name(record[1])
                        .price(Double.valueOf(record[2])).build();
                allItems.add(item);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        this.allItems.addAll(allItems);
    }

    @PostConstruct
    private void init_discount() {
        List<PromotionalRules> discountItems = new ArrayList<>();
        ClassPathResource cp = new ClassPathResource("data_discount.csv");
        try {
            FileReader fileReader = new FileReader(cp.getFile());
            CSVReader csvReader = new CSVReaderBuilder(fileReader).build();
            String[] record;

            while((record = csvReader.readNext()) != null && !record[0].isEmpty()){
                PromotionalRules promotionalRule = PromotionalRules.builder()
                        .productId(record[0])
                        .qty(Integer.valueOf(record[1]))
                        .price(Double.valueOf(record[2])).build();
                discountItems.add(promotionalRule);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        this.discountItems.addAll(discountItems);
    }

    public List<Item> getAllItems(){
        return allItems;
    }

    public List<PromotionalRules> getDiscountItems(){
        return discountItems;
    }
}
