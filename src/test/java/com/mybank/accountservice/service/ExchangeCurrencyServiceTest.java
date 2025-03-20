package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.CurrencyCode;
import com.mybank.accountservice.entity.ExchangeRateEntity;
import com.mybank.accountservice.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeCurrencyServiceTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeCurrencyService exchangeCurrencyService;

    @Test
    void getExchangeRates() {
        ExchangeRateEntity entity = new ExchangeRateEntity();
        entity.setBaseCurrency("EUR");
        entity.setConvertibleCurrency("USD");
        entity.setExchangeRate(new BigDecimal("1.11"));

        when(exchangeRateRepository.findAllByBaseCurrency("EUR")).thenReturn(List.of(entity));

        Map<CurrencyCode, BigDecimal> expected = Map.of(CurrencyCode.USD, new BigDecimal("1.11"));
        Map<CurrencyCode, BigDecimal> actual = exchangeCurrencyService.getExchangeRates(CurrencyCode.EUR);

        assertEquals(expected, actual);
    }
}