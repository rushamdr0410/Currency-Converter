package org.example.model;

public class conversionRequest {
    private String fromCurrency;
    private String toCurrency;
    private double amount;

    public conversionRequest(){}

    public conversionRequest(String fromCurrency,String toCurrency, double amount){
        this.fromCurrency=fromCurrency;
        this.toCurrency=toCurrency;
        this.amount=amount;
    }

    public String getFromCurrency(){
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency){
        this.fromCurrency=fromCurrency;
    }

    public String getToCurrency(){
        return toCurrency;
    }

    public void setToCurrency(String toCurrency){
        this.toCurrency=toCurrency;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount=amount;
    }
}
