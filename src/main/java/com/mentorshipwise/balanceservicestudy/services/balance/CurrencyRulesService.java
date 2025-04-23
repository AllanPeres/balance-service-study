package com.mentorshipwise.balanceservicestudy.services.balance;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyRulesService {


    private static final Map<String, BigDecimal> MINIMUM_AMOUNT_PER_CURRENCY = Map.of(
            "EUR", new BigDecimal("0.01"),
            "USD", new BigDecimal("5"),
            "BRL", new BigDecimal("0.01")
    );

    public BigDecimal getMinimumAmount(String currency) {
        return MINIMUM_AMOUNT_PER_CURRENCY.getOrDefault(currency, BigDecimal.ZERO);
    }
}
