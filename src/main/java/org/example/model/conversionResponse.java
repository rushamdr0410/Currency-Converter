package org.example.model;

import java.time.LocalDateTime;

public class conversionResponse {
    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double convertedAmount;
    private double exchangeRate;
    private LocalDateTime timestamp;
    private String message;

    public conversionResponse(){
        this.timestamp=LocalDateTime.now();
    }

    public conversionResponse(String fromCurrency, String toCurrency,double amount, double convertedAmount, double exchangeRate){
        this();
        this.fromCurrency=fromCurrency;
        this.toCurrency=toCurrency;
        this.amount=amount;
        this.convertedAmount=convertedAmount;
        this.exchangeRate=exchangeRate;
        this.message="Conversion successful";
    }

    public String getFromCurrency(){return fromCurrency;}
    public void setFromCurrency(String fromCurrency){this.fromCurrency=fromCurrency;}
    public double getAmount(){return amount;}
    public void setAmount(double amount){this.amount=amount;}
    public double getConvertAmount(){return convertedAmount;}
    public void setConvertedAmount(double convertedAmount){this.exchangeRate=exchangeRate;}
    public LocalDateTime getTimestamp(){return timestamp;}
    public void setTimestamp(LocalDateTime timestamp){this.timestamp=timestamp;}
    public String getMessage(){return message;}
    public void setMessage(String message){this.message=message;}
}
