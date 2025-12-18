package org.example.repository;

import org.example.model.conversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface conversionHistoryRepository extends JpaRepository<conversionHistory, Long> {
    List<conversionHistory>findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}