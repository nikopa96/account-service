package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.*;
import com.mybank.accountservice.entity.BankAccountEntity;
import com.mybank.accountservice.model.AccountBalanceInBigDecimal;
import com.mybank.accountservice.repository.BankAccountRepository;
import com.mybank.accountservice.util.ExchangeCurrencyUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final ExchangeCurrencyService exchangeCurrencyService;
    private final BankAccountRepository bankAccountRepository;

    public AccountTotalResponse getAccountTotal(String iban, String currencyCode) {
        Optional<BankAccountEntity> bankAccountOpt = bankAccountRepository.findByIban(iban);

        BankAccountEntity bankAccount = bankAccountOpt
                .orElseThrow(() -> new EntityNotFoundException("Unable to find an account for this IBAN"));

        var exchangeRates = exchangeCurrencyService.getExchangeRates("EUR");

        var balancesWithoutCurrencyConversion = bankAccount.getBalances().stream()
                .map(balance -> AccountBalanceInBigDecimal.builder()
                        .currencyCode(balance.getCurrencyCode())
                        .balance(new BigDecimal(balance.getBalance()).movePointLeft(2))
                        .build())
                .toList();

        var balancesWithCurrencyConversion = convertBalancesToSpecificCurrency(balancesWithoutCurrencyConversion,
                exchangeRates, currencyCode);
        var balanceTotal = balancesWithCurrencyConversion.stream()
                .map(AccountBalance::getConvertedBalance)
                .mapToDouble(Double::doubleValue)
                .sum();

        return AccountTotalResponse.builder()
                .account(Account.builder()
                        .iban(bankAccount.getIban())
                        .holderName(bankAccount.getHolderName())
                        .build())
                .balances(balancesWithCurrencyConversion)
                .total(AccountTotal.builder()
                        .currencyCode(CurrencyCode.valueOf(currencyCode))
                        .balanceTotal(balanceTotal)
                        .build())
                .build();
    }

    private List<AccountBalance> convertBalancesToSpecificCurrency(List<AccountBalanceInBigDecimal> balances,
                                                                   Map<String, BigDecimal> exchangeRates,
                                                                   String currencyTo) {
        return balances.stream()
                .map(balance -> AccountBalance.builder()
                        .currencyCode(CurrencyCode.valueOf(balance.getCurrencyCode()))
                        .balance(balance.getBalance().doubleValue())
                        .convertedBalanceCurrencyCode(CurrencyCode.valueOf(currencyTo))
                        .convertedBalance(ExchangeCurrencyUtil.convertToCurrency(balance.getBalance(),
                                balance.getCurrencyCode(), currencyTo, exchangeRates).doubleValue())
                        .build())
                .toList();
    }
}
