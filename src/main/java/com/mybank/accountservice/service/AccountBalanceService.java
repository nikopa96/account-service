package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.AccountResponse;
import com.mybank.accountservice.api.model.CurrencyCode;
import com.mybank.accountservice.entity.BankAccountBalanceEntity;
import com.mybank.accountservice.exception.UnprocessableEntityException;
import com.mybank.accountservice.mapper.AccountMapper;
import com.mybank.accountservice.repository.BankAccountBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountBalanceService {

    private final BankAccountBalanceRepository bankAccountBalanceRepository;
    private final AccountMapper accountMapper;

    public AccountResponse getAccountBalanceByCurrency(String iban, CurrencyCode currency) {
        Optional<BankAccountBalanceEntity> bankAccountBalanceOpt = bankAccountBalanceRepository
                .findBankAccountBalanceByIbanAndCurrency(iban, currency.getValue());

        BankAccountBalanceEntity bankAccountBalance = bankAccountBalanceOpt
                .orElseThrow(() -> new UnprocessableEntityException("Unable to find an account for this IBAN and " +
                        "currency"));

        return AccountResponse.builder()
                .account(accountMapper.toAccount(bankAccountBalance.getBankAccount()))
                .balance(accountMapper.toAccountBalance(bankAccountBalance))
                .build();
    }
}
