package com.mybank.accountservice.util;

import com.mybank.accountservice.api.model.CurrencyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangeCurrencyUtilTest {

    private Map<CurrencyCode, BigDecimal> exchangeRatesBasedOnEUR;

    @BeforeEach
    void setUp() {
        this.exchangeRatesBasedOnEUR = Map.of(
                CurrencyCode.EUR, new BigDecimal("1"),
                CurrencyCode.USD, new BigDecimal("0.92"),
                CurrencyCode.SEK, new BigDecimal("0.091"),
                CurrencyCode.RUB, new BigDecimal("0.011")
        );
    }

    @Test
    void convertToCurrency() {
        var eurToEurActual = ExchangeCurrencyUtil.convertToCurrency(new BigInteger("60000"), CurrencyCode.EUR,
                CurrencyCode.EUR, exchangeRatesBasedOnEUR);
        assertEquals(new BigDecimal("600.00"), eurToEurActual);

        var eurToUsdActual = ExchangeCurrencyUtil.convertToCurrency(new BigInteger("60000"), CurrencyCode.EUR,
                CurrencyCode.USD, exchangeRatesBasedOnEUR);
        assertEquals(new BigDecimal("652.18"), eurToUsdActual);

        var sekToUsdActual = ExchangeCurrencyUtil.convertToCurrency(new BigInteger("800000"), CurrencyCode.SEK,
                CurrencyCode.USD, exchangeRatesBasedOnEUR);
        assertEquals(new BigDecimal("791.28"), sekToUsdActual);

        var sekToRubActual = ExchangeCurrencyUtil.convertToCurrency(new BigInteger("9999"), CurrencyCode.SEK,
                CurrencyCode.RUB, exchangeRatesBasedOnEUR);
        assertEquals(new BigDecimal("827.19"), sekToRubActual);
    }
}