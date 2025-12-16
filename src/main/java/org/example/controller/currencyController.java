package org.example.controller;

import org.example.model.Currency;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class currencyController {
    private List<Currency> currencies = new ArrayList<>();
    public currencyController(){
        currencies.add(new Currency("USD","US DOLLAR","$"));
        currencies.add(new Currency("EUR","eURO","€"));
        currencies.add(new Currency("GBP","British Pound","£"));
        currencies.add(new Currency("JPY","Japanese Yen","¥"));
        currencies.add(new Currency("INR","Indian Rupee","₹"));
    }

    @GetMapping
    public List<Currency> getAllCurrencies(){
        return currencies;
    }

    @GetMapping("/{code}")
    public Currency getCurrencyByCode(@PathVariable String code){
        for(Currency currency:currencies){
            if(currency.getCode().equalsIgnoreCase(code)){
                return currency;
            }
        }
        return null;
    }

    @PostMapping
    public String addCurrency(@RequestParam String code, @RequestParam String name, @RequestParam String symbol) {
        currencies.add(new Currency(code, name, symbol));
        return "Currency" + code + "added successfully!";
    }
}
