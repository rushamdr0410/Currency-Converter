package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="conversion_history")
public class conversionHistory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =false)
    private String fromCurrency;

    @Column(nullable = false)
    private String toCurrency;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double convertedAmount;

    @Column(nullable = false)
    private Double exchangeRate;

    private LocalDateTime timestamp;

    public conversionHistory(){
        this.timestamp = LocalDateTime.now();
    }

    public conversionHistory(String fromCurrency, String toCurrency, Double amount, Double convertedAmount, Double exchangeRate){
        this();
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getFromCurrency() {return fromCurrency;}
    public void setFromCurrency(String fromCurrency) {this.fromCurrency = fromCurrency;}

    public String getToCurrency() {return toCurrency;}
    public void setToCurrency(String toCurrency) {this.toCurrency = toCurrency;}
    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}
    public Double getConvertedAmount() {return convertedAmount;}
    public void setConvertedAmount(Double convertedAmount) {this.convertedAmount = convertedAmount;}
    public Double getExchangeRate() {return exchangeRate;}
    public void setExchangeRate(Double exchangeRate) {this.exchangeRate = exchangeRate;}
    public LocalDateTime getTimestamp() {return timestamp;}
    public void setTimestamp(LocalDateTime timestamp) {this.timestamp = timestamp;}

}