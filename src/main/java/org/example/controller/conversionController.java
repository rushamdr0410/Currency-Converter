package org.example.controller;

import org.example.model.conversionResponse;
import org.example.model.conversionRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/convert")
public class conversionController {
    private Map<String,Double>exchangeRates=new HashMap<>();
    public conversionController(){
        exchangeRates.put("USD_EUR",0.85);
        exchangeRates.put("EUR_USD",1.18);
        exchangeRates.put("USD_INR", 83.5);
        exchangeRates.put("USD_JPY", 110.5);
        exchangeRates.put("USD_GBP", 0.73);
        exchangeRates.put("GBP_USD", 1.37);
        exchangeRates.put("INR_USD", 0.012);
    }

    @GetMapping("/simple")
    public conversionResponse convertSimple(@RequestParam String from, @RequestParam String to, @RequestParam double amount){
        String rateKey=from.toUpperCase()+"_"+to.toUpperCase();
        Double rate=exchangeRates.get(rateKey);

        if (rate==null){
            return createErrorResponse("Exchange Rates are not available"+from+"to"+to);
        }
        double convertedAmount=rate*amount;
        return new conversionResponse(from.toUpperCase(),to.toUpperCase(),amount,convertedAmount,rate);
    }

    @PostMapping
    public conversionResponse convert(@RequestBody conversionRequest request){
        String from=request.getFromCurrency().toUpperCase();
        String to=request.getToCurrency().toUpperCase();
        double amount=request.getAmount();

        String rateKey=from+"_"+to;
        Double rate=exchangeRates.get(rateKey);
        if (rate==null){
            return createErrorResponse("Exchange rate not available for"+from+"to"+to);
        }
        double convertedAmount=rate*amount;

        return new conversionResponse(from,to,amount,convertedAmount,rate);
    }

    private conversionResponse createErrorResponse(String errorMessage){
        conversionResponse response=new conversionResponse();
        response.setMessage("Error:"+errorMessage);
        return response;
    }

    @GetMapping("/rates")
    public Map<String, Double>getAllRates(){
        return exchangeRates;
    }
}
