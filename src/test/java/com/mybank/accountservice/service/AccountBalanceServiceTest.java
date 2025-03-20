package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.AccountBalanceProperties;
import com.mybank.accountservice.entity.BankAccountBalanceEntity;
import com.mybank.accountservice.repository.BankAccountBalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountBalanceServiceTest {

    @Mock
    private BankAccountBalanceRepository bankAccountBalanceRepository;

    @InjectMocks
    private AccountBalanceService accountBalanceService;

    @Test
    void getAccountBalanceProperties() {
        BankAccountBalanceEntity entity = new BankAccountBalanceEntity();
        entity.setUuid(UUID.fromString("2430d6ab-2a68-4dae-83b4-c00e812f3827"));
        entity.setCurrencyCode("SEK");
        entity.setBalance(new BigInteger("500000"));

        when(bankAccountBalanceRepository.findBankAccountBalanceByIbanAndCurrency("123", "SEK"))
                .thenReturn(Optional.of(entity));

        var expected = AccountBalanceProperties.builder()
                .bankAccountBalanceUuid(UUID.fromString("2430d6ab-2a68-4dae-83b4-c00e812f3827"))
                .build();
        var actual = accountBalanceService.getAccountBalanceProperties("123", "SEK");

        assertEquals(expected, actual);
    }

    @Test
    void getAccountBalanceProperties_EntityNotFoundException() {
        when(bankAccountBalanceRepository.findBankAccountBalanceByIbanAndCurrency("123", "SEK"))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> accountBalanceService.getAccountBalanceProperties("123", "SEK"));
    }
}