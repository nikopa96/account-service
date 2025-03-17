package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.CurrencyCode;
import com.mybank.accountservice.entity.BankAccountBalanceEntity;
import com.mybank.accountservice.entity.ConversionRateEntity;
import com.mybank.accountservice.repository.ConversionRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyConversionService {

    private final ConversionRateRepository conversionRateRepository;

    public List<BankAccountBalanceEntity> convertBalanceToCurrency(List<BankAccountBalanceEntity> originalBalances,
                                                                   CurrencyCode currencyCode) {
        var conversionRates = conversionRateRepository.findAllByCurrencyCodeFrom(CurrencyCode.EUR.getValue());
        Map<String, BigInteger> conversionRatesInEUR = conversionRates.stream()
                .collect(Collectors.toMap(
                        ConversionRateEntity::getCurrencyCodeTo,
                        ConversionRateEntity::getRateInCents
                ));

        return originalBalances.stream()
                .peek(balance -> {
                    BigInteger convertedBalance = convertCentsToCurrency(balance.getBalance(),
                            balance.getCurrencyCode(), currencyCode.getValue(), conversionRatesInEUR);
                    balance.setBalance(convertedBalance);
                    balance.setCurrencyCode(currencyCode.getValue());
                }).collect(Collectors.toList());
    }

    public static BigInteger convertCentsToCurrency(BigInteger amountInCents, String fromCurrency, String toCurrency,
                                                    Map<String, BigInteger> conversionRates) {
        BigInteger fromRate = conversionRates.get(fromCurrency);
        BigInteger toRate = conversionRates.get(toCurrency);

        BigInteger amountInEuroCents = amountInCents.multiply(fromRate).divide(new BigInteger("100"));
        return amountInEuroCents.multiply(new BigInteger("100")).divide(toRate);
    }
}
