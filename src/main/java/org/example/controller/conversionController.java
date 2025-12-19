package org.example.controller;

import org.example.model.*;
import org.example.repository.conversionHistoryRepository;
import org.hibernate.query.sqm.tree.expression.Conversion;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.service.ExchangeRateService;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/convert")
public class conversionController {

    private Map<String,Double>exchangeRates=new HashMap<>();
    @Autowired
    private conversionHistoryRepository historyRepository;
    @Autowired
    private ExchangeRateService exchangeRateService;

    public conversionController(){
        initializeRates();
    }
    public void initializeRates(){
        exchangeRates.put("USD_EUR",0.85);
        exchangeRates.put("EUR_USD",1.18);
        exchangeRates.put("USD_INR", 83.5);
        exchangeRates.put("USD_JPY", 110.5);
        exchangeRates.put("USD_GBP", 0.73);
        exchangeRates.put("GBP_USD", 1.37);
        exchangeRates.put("INR_USD", 0.012);
    }

    @GetMapping("/simple")
    public Mono<conversionResponse> convertSimple(@RequestParam String from, @RequestParam String to, @RequestParam double amount){
        return exchangeRateService.getExchangeRate(from.toUpperCase(), to.toUpperCase()).map(rate->{
            double convertedAmount = amount *rate;
            conversionResponse response= new conversionResponse(from.toUpperCase(), to.toUpperCase(), amount, convertedAmount, rate);
            saveConversionHistory(response);
            return response;
        }).onErrorReturn(createErrorResponse("Conversion failed"));
    }

    @PostMapping
    public Mono<conversionResponse> convert(@RequestBody conversionRequest request){
        return exchangeRateService.getExchangeRate(request.getFromCurrency().toUpperCase(),request.getToCurrency().toUpperCase()).map(rate ->{
            double convertedAmount=request.getAmount()*rate;

            conversionResponse response=new conversionResponse(request.getFromCurrency().toUpperCase(), request.getToCurrency().toUpperCase(), request.getAmount(),convertedAmount,rate);

            saveConversionHistory(response);

            return response;
        }).onErrorReturn(createErrorResponse("Conversion Failed"));

    }

    @GetMapping("/history")
    public List<conversionHistory>getHistory(@RequestParam(required = false)String from, @RequestParam(required = false)String to){
        if(from!=null && to!=null){
            return historyRepository.findByFromCurrencyAndToCurrency(from.toUpperCase(),to.toUpperCase());
        }else{
            return historyRepository.findAll();
        }
    }

    private conversionResponse performConversion(String from, String to, double amount){
        String rateKey = from.toUpperCase()+"_"+to.toUpperCase();
        Double rate = exchangeRates.get(rateKey);
        if(rate==null){
            return createErrorResponse("Exchange rate not available for"+from+"to"+to);
        }
        double convertedAmount = rate*amount;
        return new conversionResponse(from.toUpperCase(),to.toUpperCase(),amount,convertedAmount,rate);
    }

    private void saveConversionHistory(conversionResponse response){
        conversionHistory history=new conversionHistory(response.getFromCurrency(), response.getToCurrency(),response.getAmount(),response.getConvertAmount(),response.getExchangeRate());

        historyRepository.save(history);
        System.out.println("Saved conversion to database"+history);
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
