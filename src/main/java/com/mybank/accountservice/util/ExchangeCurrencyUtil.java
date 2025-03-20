package com.mybank.accountservice.util;

import com.mybank.accountservice.api.model.CurrencyCode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;

public class ExchangeCurrencyUtil {

    public static BigDecimal convertToCurrency(BigInteger amount, CurrencyCode currencyFrom, CurrencyCode currencyTo,
                                               Map<CurrencyCode, BigDecimal> exchangeRates) {
        BigDecimal amountDecimal = new BigDecimal(amount).movePointLeft(2);

        BigDecimal currencyFromRate = exchangeRates.get(currencyFrom);
        BigDecimal currencyToRate = exchangeRates.get(currencyTo);

        return currencyFromRate.divide(currencyToRate, 5, RoundingMode.HALF_EVEN).multiply(amountDecimal)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
