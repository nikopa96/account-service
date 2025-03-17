package com.mybank.accountservice.controller;

import com.mybank.accountservice.api.AccountApi;
import com.mybank.accountservice.api.model.AccountResponse;
import com.mybank.accountservice.api.model.AccountTotalRequest;
import com.mybank.accountservice.api.model.AccountTotalResponse;
import com.mybank.accountservice.api.model.CurrencyCode;
import com.mybank.accountservice.service.AccountBalanceService;
import com.mybank.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;
    private final AccountBalanceService accountBalanceService;

    @Override
    public ResponseEntity<AccountResponse> getBankAccountBalanceByCurrency(String iban, CurrencyCode currencyCode) {
        return ResponseEntity.ok(accountBalanceService.getAccountBalanceByCurrency(iban, currencyCode));
    }

    @Override
    public ResponseEntity<AccountTotalResponse> getBankAccountTotal(AccountTotalRequest request) {
        return ResponseEntity.ok(accountService.getAccountTotal(request.getIban(), request.getCurrencyCode()));
    }
}
