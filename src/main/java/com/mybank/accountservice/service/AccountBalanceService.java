package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.AccountBalanceProperties;
import com.mybank.accountservice.entity.BankAccountBalanceEntity;
import com.mybank.accountservice.repository.BankAccountBalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountBalanceService {

    private final BankAccountBalanceRepository bankAccountBalanceRepository;

    public AccountBalanceProperties getAccountBalanceProperties(String iban, String currency) {
        Optional<BankAccountBalanceEntity> bankAccountBalanceOpt = bankAccountBalanceRepository
                .findBankAccountBalanceByIbanAndCurrency(iban, currency);

        BankAccountBalanceEntity bankAccountBalance = bankAccountBalanceOpt
                .orElseThrow(() -> new EntityNotFoundException("Unable to find an account balance by this IBAN and " +
                        "currency code"));

        return AccountBalanceProperties.builder()
                .bankAccountBalanceUuid(bankAccountBalance.getUuid())
                .build();
    }
}
