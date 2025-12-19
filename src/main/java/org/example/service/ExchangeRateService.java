package org.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {
    private final String API_URL="https://api.exchangerate-api.com/v4/latest";
    private final WebClient webClient;
    private Map<String, Map<String, Double>>rateCache=new HashMap<>();
    public ExchangeRateService(WebClient.Builder webClientBuilder) {
        this.webClient=webClientBuilder.baseUrl(API_URL).build();
    }
    public Mono<Double>getExchangeRate(String fromCurrency, String toCurrency){
        if(rateCache.containsKey(fromCurrency)){
            Map<String,Double>rates=rateCache.get(fromCurrency);
            if(rates.containsKey(toCurrency)){
                return Mono.just(rates.get(toCurrency));
            }
        }
        return fetchExchangeRate(fromCurrency, toCurrency);
    }
    private Mono<Double>fetchExchangeRate(String fromCurrency, String toCurrency){
        return webClient.get().uri("/{fromCurrency}",fromCurrency).retrieve().bodyToMono(Map.class).map(response -> {
            Map<String,Object>rates=(Map<String, Object>) response.get("rates");
            Double rate=((Number) rates.get(toCurrency)).doubleValue();
            cacheRates(fromCurrency, rates);
            return rate;
        }).onErrorResume(e->{
            System.out.println("API Error:"+e.getMessage()+".Using mock data.");
            return Mono.just(getMockRate(fromCurrency, toCurrency));
        });
    }
    private void cacheRates(String baseCurrency, Map<String, Object>rates){
        Map<String, Double>convertedRates=new HashMap<>();
        for(Map.Entry<String,Object>entry:rates.entrySet()){
            convertedRates.put(entry.getKey(),((Number) entry.getValue()).doubleValue());
        }
        rateCache.put(baseCurrency, convertedRates);
    }

    private Double getMockRate(String fromCurrency, String toCurrency){
        Map<String, Double>mockRates = new HashMap<>();
        if (fromCurrency.equals("USD")){
            mockRates.put("EUR", 0.85);
            mockRates.put("GBP", 0.73);
            mockRates.put("INR", 83.5);
            mockRates.put("JPY", 110.5);
            mockRates.put("USD", 1.0);
        }else if (fromCurrency.equals("EUR")){
            mockRates.put("USD", 1.18);
            mockRates.put("GBP", 0.86);
            mockRates.put("INR", 98.2);
        }else{
            return 1.0;
        }
        return mockRates.getOrDefault(fromCurrency,1.0);
    }

    public void clearCache(){
        rateCache.clear();
    }
}
