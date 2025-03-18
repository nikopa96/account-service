package com.mybank.accountservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class ExchangeCurrencyUtil {

    public static BigDecimal convertToCurrency(BigDecimal amount, String currencyFrom, String currencyTo,
                                               Map<String, BigDecimal> exchangeRates) {
        BigDecimal currencyFromRate = exchangeRates.get(currencyFrom);
        BigDecimal currencyToRate = exchangeRates.get(currencyTo);

        return currencyFromRate.divide(currencyToRate, RoundingMode.HALF_EVEN).multiply(amount)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
