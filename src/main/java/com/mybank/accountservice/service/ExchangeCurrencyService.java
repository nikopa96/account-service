package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.CurrencyCode;
import com.mybank.accountservice.entity.ExchangeRateEntity;
import com.mybank.accountservice.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeCurrencyService {

    private final ExchangeRateRepository exchangeRateRepository;

    public Map<CurrencyCode, BigDecimal> getExchangeRates(CurrencyCode baseCurrency) {
        var exchangeRates = exchangeRateRepository.findAllByBaseCurrency(baseCurrency.getValue());

        return exchangeRates.stream()
                .collect(Collectors.toMap(
                        exchangeRate -> CurrencyCode.valueOf(exchangeRate.getConvertibleCurrency()),
                        ExchangeRateEntity::getExchangeRate
                ));
    }
}
