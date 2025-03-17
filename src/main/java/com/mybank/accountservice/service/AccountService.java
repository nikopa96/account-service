package com.mybank.accountservice.service;

import com.mybank.accountservice.api.model.AccountTotal;
import com.mybank.accountservice.api.model.AccountTotalResponse;
import com.mybank.accountservice.api.model.CurrencyCode;
import com.mybank.accountservice.entity.BankAccountBalanceEntity;
import com.mybank.accountservice.entity.BankAccountEntity;
import com.mybank.accountservice.exception.UnprocessableEntityException;
import com.mybank.accountservice.mapper.AccountMapper;
import com.mybank.accountservice.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final CurrencyConversionService currencyConversionService;
    private final BankAccountRepository bankAccountRepository;
    private final AccountMapper accountMapper;

    public AccountTotalResponse getAccountTotal(String iban, CurrencyCode currencyCode) {
        Optional<BankAccountEntity> bankAccountOpt = bankAccountRepository.findByIban(iban);

        BankAccountEntity bankAccount = bankAccountOpt
                .orElseThrow(() -> new UnprocessableEntityException("Unable to find an account for this IBAN"));

        List<BankAccountBalanceEntity> convertedAccountBalances = currencyConversionService
                .convertBalanceToCurrency(bankAccount.getBalances(), currencyCode);

        return AccountTotalResponse.builder()
                .account(accountMapper.toAccount(bankAccount))
                .balances(accountMapper.toAccountBalances(convertedAccountBalances))
                .total(AccountTotal.builder()
                        .currencyCode(currencyCode)
                        .balance(null)
                        .build())
                .build();
    }
}
