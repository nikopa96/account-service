package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.*;
import com.mybank.accountservice.entity.BankAccountBalanceEntity;
import com.mybank.accountservice.entity.BankAccountEntity;
import com.mybank.accountservice.repository.BankAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private ExchangeCurrencyService exchangeCurrencyService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void getAccountTotal() {
        BankAccountBalanceEntity balanceEntity = new BankAccountBalanceEntity();
        balanceEntity.setUuid(UUID.fromString("2430d6ab-2a68-4dae-83b4-c00e812f3827"));
        balanceEntity.setCurrencyCode("EUR");
        balanceEntity.setBalance(new BigInteger("30000"));

        BankAccountEntity entity = new BankAccountEntity();
        entity.setUuid(UUID.fromString("00aa97ae-0846-464c-8831-dc0cb2e1b7a8"));
        entity.setIban("EE281248567242716482");
        entity.setHolderName("TEST TEST");
        entity.setBalances(List.of(balanceEntity));

        Map<CurrencyCode, BigDecimal> exchangeRates = Map.of(CurrencyCode.USD, new BigDecimal("0.92"),
                CurrencyCode.EUR, new BigDecimal("1.0"));

        when(bankAccountRepository.findByIban("EE281248567242716482")).thenReturn(Optional.of(entity));
        when(exchangeCurrencyService.getExchangeRates(CurrencyCode.EUR)).thenReturn(exchangeRates);

        var expected = AccountTotalResponse.builder()
                .account(Account.builder()
                        .iban("EE281248567242716482")
                        .holderName("TEST TEST")
                        .build())
                .balances(List.of(AccountBalance.builder()
                        .currencyCode(CurrencyCode.EUR)
                        .balance(300.0)
                        .convertedBalanceCurrencyCode(CurrencyCode.USD)
                        .convertedBalance(326.09)
                        .build()))
                .total(AccountTotal.builder()
                        .currencyCode(CurrencyCode.USD)
                        .balanceTotal(326.09)
                        .build())
                .build();
        var actual = accountService.getAccountTotal("EE281248567242716482", CurrencyCode.USD);

        assertEquals(expected, actual);
    }

    @Test
    void getAccountTotal_EntityNotFoundException() {
        when(bankAccountRepository.findByIban("123")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountTotal("123", eq(CurrencyCode.EUR)));
    }
}